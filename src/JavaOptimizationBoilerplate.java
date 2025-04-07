import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class JavaOptimizationBoilerplate {

    // 1. String concatenation in loop (BAD)
    public String buildStringBad(int n) {
        String result = "";
        for (int i = 0; i < n; i++) {
            result += i; // inefficient: creates new string each time
        }
        return result;
    }

    // 1. StringBuilder usage (GOOD)
    public String buildStringWithBuilder(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(i);
        }
        return sb.toString();
    }
    // ------------------------------------------------------------------------------------------
    // 2. Unnecessary object creation (BAD)
    public void stringCreation() {
        String title1 = "Effective Java";
        // Création d’un nouvel objet dans le heap, même si "Effective Java" existe déjà dans le pool
        String title2 = new String("Effective Java");

        // Résultat : tu as deux objets différents en mémoire
        System.out.println(title1 == title2); // false → références différentes
        System.out.println(title1.equals(title2)); // true → contenu identique
    }

    // 2.2 ValueOf usage (GOOD)
    public void stringPool(boolean flag) {
        // Elle est stockée dans le String pool : une zone spéciale de la mémoire JVM où les littéraux de type String sont mis en cache
        String title1 = "Effective Java";
        String title2 = "Effective Java";

        System.out.println(title1 == title2); // true → même objet (référence identique)
    }

    // 2.2 ValueOf usage (GOOD)
    public Boolean getBooleanValue(boolean flag) {
        return Boolean.valueOf(flag);
    }

    // ------------------------------------------------------------------------------------------
    // 3. Wrapper in loop (BAD)
    public Integer sumWrapper(int limit) {
        Integer sum = 0;
        for (int i = 0; i < limit; i++) {
            sum += i; // causes boxing/unboxing
        }
        return sum;
    }

    // 3. Primitive usage (GOOD)
    public int sumPrimitive(int limit) {
        int sum = 0;
        for (int i = 0; i < limit; i++) {
            sum += i;
        }
        return sum;
    }
    // ------------------------------------------------------------------------------------------
    // 4. Use final for constants (GOOD)
    public static final int THREAD_COUNT = 4;
    // ------------------------------------------------------------------------------------------
    // 5. Index loop (BAD)
    public void printNamesBad(List<String> names) {
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
        }
    }

    // 5. Enhanced for-loop (GOOD)
    public void printNames(List<String> names) {
        for (String name : names) {
            System.out.println(name);
        }
    }
    // ------------------------------------------------------------------------------------------
    // 6. No initial capacity (BAD)
    public Map<String, String> createMapWithoutCapacity() {
        return new HashMap<>(); // might resize on large data
    }

    // 6. Initial capacity specified (GOOD)
    public Map<String, String> createMapWithCapacity(int size) {
        return new HashMap<>(size);
    }
    // ------------------------------------------------------------------------------------------
    // 7. Stream in trivial case (BAD)
    public List<String> filterNamesBad(List<String> names) {
        List<String> result = new ArrayList<>();
        for (String name : names) {
            if (name.startsWith("A")) {
                result.add(name);
            }
        }
        return result;
    }

    // 7. Stream for readability (GOOD)
    public List<String> filterNames(List<String> names) {
        return names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
    }
    // ------------------------------------------------------------------------------------------
    // 8. Eager initialization (BAD)
    private Map<String, String> eagerCache = new HashMap<>();

    // 8. Lazy initialization (GOOD)
    private Map<String, String> cache;

    public Map<String, String> getCache() {
        if (cache == null) {
            cache = new HashMap<>();
        }
        return cache;
    }
    // ------------------------------------------------------------------------------------------
    // 9. Large synchronized block (BAD)
    public synchronized void updateDataBad() {
        // unnecessary full method lock
    }

    // 9. Small synchronized block (GOOD)
    public void updateData() {
        synchronized (this) {
            // only thread-sensitive logic here
        }
    }
    // ------------------------------------------------------------------------------------------
    // 10. Volatile for visibility (GOOD)
    private volatile boolean running = true;

    public boolean isRunning() {
        return running;
    }
    // ------------------------------------------------------------------------------------------
    // 11. Recompiled regex (BAD)
    public boolean isDigitsBad(String input) {
        return input.matches("\\d+"); // compiles regex every call
    }

    // 11. Precompiled regex (GOOD)
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d+");

    public boolean isDigits(String input) {
        return DIGIT_PATTERN.matcher(input).matches();
    }
    // ------------------------------------------------------------------------------------------
    // 12. Try-catch in loop (BAD)
    public List<Integer> parseIntegersBad(List<String> input) {
        List<Integer> result = new ArrayList<>();
        for (String s : input) {
            try {
                result.add(Integer.parseInt(s));
            } catch (NumberFormatException e) {
                // ignore
            }
        }
        return result;
    }
    // ------------------------------------------------------------------------------------------
    // 12. Precheck instead of exception (GOOD)
    public List<Integer> parseIntegers(List<String> input) {
        List<Integer> result = new ArrayList<>();
        for (String s : input) {
            if (DIGIT_PATTERN.matcher(s).matches()) {
                result.add(Integer.parseInt(s));
            }
        }
        return result;
    }
    // ------------------------------------------------------------------------------------------
    // 13. Synchronized list (BAD if no concurrency needed)
    public List<String> createSynchronizedList() {
        return Collections.synchronizedList(new ArrayList<>());
    }

    // 13. Proper structures (GOOD)
    public List<String> createArrayList() {
        return new ArrayList<>();
    }

    public Map<String, Object> createConcurrentMap() {
        return new ConcurrentHashMap<>();
    }
    // ------------------------------------------------------------------------------------------
    // 14. Mutable class (BAD)
    public static class MutablePerson {
        private String name;
        private int age;

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
    // ------------------------------------------------------------------------------------------
    // 14. Immutable class (GOOD)
    public static final class Person {
        private final String name;
        private final int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}