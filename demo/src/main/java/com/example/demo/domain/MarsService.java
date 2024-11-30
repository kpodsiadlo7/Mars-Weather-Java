package com.example.demo.domain;

import com.example.demo.NasaClient;
import com.example.demo.dto.WeatherDetailsDto;
import com.example.demo.model.MarsDailyWeather;
import com.example.demo.model.RefreshTimeInfo;
import com.example.demo.repo.DataRefreshRepo;
import com.example.demo.repo.MarsWeatherRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarsService {

    private static final Logger log = LoggerFactory.getLogger(MarsService.class);
    private final MarsWeatherRepo marsWeatherRepo;
    private final DataRefreshRepo dataRefreshRepo;
    private final MarsMapper marsMapper;
    private final NasaClient nasaClient;

    private void saveMarsWeatherDetails(WeatherDetailsDto weatherDetailsDto) {
        if (isSolesPresent(weatherDetailsDto)) {
            List<MarsDailyWeather> marsDays = new ArrayList<>();
            for (var day : weatherDetailsDto.soles()) {
                if (hasNullFields(day))
                    continue;
                marsDays.add(marsMapper.toEntityFromDto(day));
            }

            /* Zadanie dodatkowe, zamienić pętle na stream
            List<MarsDailyWeather> marsDays = weatherDetailsDto.soles().stream()
                    .filter(this::hasNullFields)
                    .map(marsMapper::toEntityFromDto)
                    .collect(Collectors.toList());
             */

            marsWeatherRepo.saveAll(marsDays);
            dataRefreshRepo.save(new RefreshTimeInfo());
        }
    }

    private static boolean isSolesPresent(WeatherDetailsDto weatherDetailsDto) {
        return weatherDetailsDto != null && weatherDetailsDto.soles() != null && !weatherDetailsDto.soles().isEmpty();
    }

    //if (day.terrestrialDate() == null || day.sol() == null || day.ls() == null || day.season() == null || day.min_temp() == null || day.max_temp() == null || day.pressure() == null || day.atmo_opacity() == null || day.sunrise() == null || day.sunset() == null || day.min_gts_temp() == null || day.max_gts_temp() == null)
    private boolean hasNullFields(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true); // Umożliwia dostęp do prywatnych pól
            try {
                if (field.get(obj) == null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Arrays.toString(obj.getClass().getDeclaredFields()));
        return false;
    }

    public void initializeData() {
        if (isWeatherDataPresentInDb()) {
            Optional<RefreshTimeInfo> lastRefreshData = dataRefreshRepo.findFirstByOrderByLastRefreshTimeDesc();
            if (isDataOutdated(lastRefreshData)) {
                // TODO: Jeżeli dane są przestarzałe, będziemy musieli zaktualizować naszą bazę danych. Nie będzie to polegało na ponownym wrzuceniu tych danych do bazy, ponieważ zdublowalibyśmy wiele informacji. Musimy uniknąć duplikatów, więc konieczne będzie stworzenie logiki odpowiedzialnej za ich pominięcie. Zajmiemy się tym w następnym odcinku
                synchronizeData();
            }
        } else {
            // Proponowana zmiana nazwy na 'fetchWeatherDetailsAndSaveToDb'
            saveMarsWeatherDetails(getWeatherDetailsFromNasa());
        }
    }

    public WeatherDetailsDto getWeatherDetailsFromNasa() {
        return nasaClient.getWeatherDetails();
    }

    private void synchronizeData() {
        List<MarsDailyWeather> weatherListFromDb = getWeatherDetailsFromDb();
        WeatherDetailsDto weatherDetailsDto = getWeatherDetailsFromNasa();
        List<MarsDailyWeather> uniqueRecords = new ArrayList<>();
        if (isSolesPresent(weatherDetailsDto)){
            for (var fromNasa : weatherDetailsDto.soles()){
                MarsDailyWeather entityFromDto = marsMapper.toEntityFromDto(fromNasa);
                // propozycja zmiany nazwy na 'isUniqueDay'
                if (!weatherListFromDb.contains(entityFromDto)) {
                    uniqueRecords.add(entityFromDto);
                }
                if (!uniqueRecords.isEmpty()){
                    marsWeatherRepo.saveAll(uniqueRecords);
                }
            }
        } else {
            log.error("Brak soles w response z NASA API");
        }
    }

    /* propozycja aby napisać to w taki sposób
    private void synchronizeData() {
    List<MarsDailyWeather> weatherListFromDb = getWeatherDetailsFromDb();
    WeatherDetailsDto weatherDetailsDto = getWeatherDetailsFromNasa();

    if (isSolesPresent(weatherDetailsDto)) {
        List<MarsDailyWeather> uniqueRecords = weatherDetailsDto.soles().stream()
                .map(marsMapper::toEntityFromDto)
                // propozycja zmiany nazwy na 'isUniqueDay' zamiast używania wykrzykników
                .filter(entityFromDto -> !weatherListFromDb.contains(entityFromDto))
                .collect(Collectors.toList());

        if (!uniqueRecords.isEmpty()) {
            marsWeatherRepo.saveAll(uniqueRecords);
        }
    } else {
        log.error("Brak soles w response z NASA API");
        }
    }

     */

    private boolean isWeatherDataPresentInDb() {
        return marsWeatherRepo.count() > 0;
    }

    private static boolean isDataOutdated(Optional<RefreshTimeInfo> lastRefreshData) {


        //private static final int DAYS_UNTIL_DATA_IS_STALE = 7;


        return lastRefreshData.isPresent() && lastRefreshData.get().getLastRefreshTime().plusDays(7).isBefore(LocalDateTime.now());
    }

    public List<MarsDailyWeather> getWeatherDetailsFromDb() {
        return marsWeatherRepo.findAll();
    }
}
