package searchengine.lemmatizer;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.english.EnglishLuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LemmaAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(LemmaAnalyzer.class);

    // Стоп-слова вынесены в статические множества
    private static final Set<String> STOP_WORDS = new HashSet<>();

    static {
        Collections.addAll(STOP_WORDS,
                // Русские стоп-слова
                "и", "в", "во", "не", "что", "он", "на", "я", "с", "со", "как", "а", "то",
                "все", "она", "так", "его", "но", "да", "ты", "к", "у", "же", "вы", "за",
                "бы", "по", "только", "ее", "мне", "было", "вот", "от", "меня", "еще", "нет",
                "о", "из", "ему", "теперь", "когда", "даже", "ну", "ли", "если", "уже",
                "или", "ни", "быть", "был", "него", "до", "вас", "нибудь", "опять", "уж",
                "с", "то", "же", "все", "это", "так", "как", "но", "на", "по", "из", "от",

                // Английские стоп-слова
                "a", "an", "the", "and", "or", "but", "in", "on", "at", "to", "for", "of",
                "with", "by", "from", "up", "about", "into", "through", "during", "before",
                "after", "above", "below", "between", "among", "is", "are", "was", "were",
                "be", "been", "being", "have", "has", "had", "do", "does", "did", "will",
                "would", "shall", "should", "may", "might", "must", "can", "could", "this",
                "that", "these", "those", "i", "you", "he", "she", "it", "we", "they",
                "also", "over"
        );
    }

    // Singleton для морфологии
    private static final LuceneMorphology RUSSIAN_MORPH;
    private static final LuceneMorphology ENGLISH_MORPH;

    static {
        try {
            RUSSIAN_MORPH = new RussianLuceneMorphology();
            ENGLISH_MORPH = new EnglishLuceneMorphology();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации морфологии", e);
        }
    }

    public static void main(String[] args) {
        String text = "Beautiful cats play with ball. Красивые кошки играют с мячом. " +
                "Dogs also like to play. Собаки тоже любят играть. " +
                "The quick brown fox jumps over the lazy dog. " +
                "Быстрая коричневая лиса прыгает через ленивую собаку. " +
                "Programming and программирование are interesting activities. " +
                "Java and Java - powerful technologies.";

        log.info("Исходный текст:\n{}", text);

        Map<String, Integer> lemmas = extractMultiLanguageLemmas(text);

        Map<String, Integer> russianLemmas = new HashMap<>();
        Map<String, Integer> englishLemmas = new HashMap<>();

        for (Map.Entry<String, Integer> entry : lemmas.entrySet()) {
            if (isCyrillic(entry.getKey())) {
                russianLemmas.put(entry.getKey(), entry.getValue());
            } else {
                englishLemmas.put(entry.getKey(), entry.getValue());
            }
        }

        printResults("Английские леммы", englishLemmas);
        printResults("Русские леммы", russianLemmas);

        log.info("Всего английских лемм: {}", englishLemmas.size());
        log.info("Всего русских лемм: {}", russianLemmas.size());
        log.info("Всего уникальных лемм: {}", lemmas.size());
    }

    private static Map<String, Integer> extractMultiLanguageLemmas(String text) {
        Map<String, Integer> lemmas = new HashMap<>();

        String cleanText = text.replaceAll("<[^>]+>", " ")
                .replaceAll("&[^;]+;", " ")
                .replaceAll("[^a-zA-Zа-яёА-ЯЁ\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        log.debug("Очищенный текст:\n{}", cleanText);

        for (String word : cleanText.split("\\s+")) {
            if (word.length() < 2 || STOP_WORDS.contains(word.toLowerCase())) continue;

            String lowerWord = word.toLowerCase();
            boolean isRussian = isCyrillic(lowerWord);
            boolean isEnglish = isLatin(lowerWord);

            if (!isRussian && !isEnglish) continue;

            try {
                LuceneMorphology morph = isRussian ? RUSSIAN_MORPH : ENGLISH_MORPH;
                List<String> normalForms = morph.getNormalForms(lowerWord);

                if (!normalForms.isEmpty() && !isFunctionalWord(morph, lowerWord)) {
                    String lemma = normalForms.get(0);
                    lemmas.put(lemma, lemmas.getOrDefault(lemma, 0) + 1);
                    log.debug("{}: '{}' → '{}'", isRussian ? "RU" : "EN", word, lemma);
                }
            } catch (Exception e) {
                log.warn("Не распознано слово: {}", word);
            }
        }

        return lemmas;
    }

    private static void printResults(String title, Map<String, Integer> map) {
        log.info("\n{}:\n{}", title, "-".repeat(40));
        map.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry -> log.info("{}: {} → {} раз",
                        title.startsWith("Анг") ? "EN" : "RU",
                        entry.getKey(), entry.getValue()));
    }

    private static boolean isCyrillic(String word) {
        return word.matches("[а-яё]+");
    }

    private static boolean isLatin(String word) {
        return word.matches("[a-z]+");
    }

    private static boolean isFunctionalWord(LuceneMorphology morphology, String word) {
        try {
            String info = morphology.getMorphInfo(word).toString().toUpperCase();
            if (morphology instanceof RussianLuceneMorphology) {
                return info.contains("МЕЖД") || info.contains("ПРЕДЛ") || info.contains("СОЮЗ") ||
                        info.contains("МС") || info.contains("ЧАСТ") || info.contains("МЕСТОИМ");
            } else {
                return info.contains("PREP") || info.contains("CONJ") || info.contains("ARTICLE") ||
                        info.contains("PRON") || info.contains("PART") || info.contains("DET");
            }
        } catch (Exception e) {
            return false;
        }
    }
}
