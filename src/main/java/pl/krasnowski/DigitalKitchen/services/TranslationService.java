package pl.krasnowski.DigitalKitchen.services;

import org.springframework.stereotype.Service;

@Service
public interface TranslationService {
    String translateFood(String keyword, String inputLanguage, String outputLanguage);

    String translateWorkout(String keyword, String inputLanguage, String outputLanguage);

}
