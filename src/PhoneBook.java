import java.util.*;

public class PhoneBook {

    public static void main(String[] args) {
        List<String[]> book = new ArrayList<String[]>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя пользователя или номер телефона.\nДля просмотра книги введите команду list.\n" +
                "Для выхода введите команду exit.");
        while (true) {
            String scan = scanner.nextLine();
            if (scan.replaceAll("\\D", "").length() > 0){
                checkItemInPhoneBook(book, scanItem(scan, scanner, false), scanner, false);
            }
            else {
                if (scan.equals("list")) {
                    list(book);
                }
                else if(scan.equals("exit")){
                    System.exit(0);
                }
                else {
                    checkItemInPhoneBook(book, scanItem(scan, scanner, true), scanner, true);
                }
            }
        }
    }

    public static void checkItemInPhoneBook(List<String[]>book, String checkingValue, Scanner scanner, boolean isName) {
        String pattern1 = isName ? "имени" : "номера телефона";
        String pattern2 = isName ? "номер телефона" : "имя";
        String item = scanPhoneBook(book, checkingValue, isName);
        if (item.equals("")){
            System.out.printf("Такого %s нет в телефонной книге. Для внесения записи введите %s:\n", pattern1, pattern2);
            String secondValue = scanItem("", scanner, !isName);
            if (isName) {
                add(book, checkingValue, secondValue);
            } else {
                add(book, secondValue, checkingValue);
            }
        }
        else {
            System.out.printf("Для искомого значения в книге найдено соответсвие: %s\n", item);
        }
    }

    public static String scanItem(String item, Scanner scanner, boolean isName) {
        String result = "";
        String pattern = isName ? "имени" : "номера телефона";
        boolean isCorrect = false;
        if (item.equals("")){
            item = scanner.nextLine();
        }
        while (!isCorrect) {
            isCorrect = isName ? checkName(item) : checkPhoneNumber(item);

            if (!isCorrect) {
                System.out.printf("Введите корректное значение %s!\n", pattern);
                item = scanner.nextLine();
                continue;
            } else {
                result = isName ? formatName(item) : formatPhoneNumber(item);
            }
        }
        return result;
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        String clean = phoneNumber.replaceAll("\\D", "");
        return clean.length() == 11 & (clean.charAt(0) == '7' | clean.charAt(0) == '8');
    }

    private static String formatPhoneNumber(String phoneNumber) {
        String clean = phoneNumber.replaceAll("\\D", "");
        String result = "+7" + " " + clean.substring(1, 4) + " " +
                clean.substring(4, 7) + " " + clean.substring(7, 9) + " " + clean.substring(9);
        return result;
    }

    public static boolean checkName(String name) {
        String[] fio = name.strip().split(" ");
        return fio.length == 3;
    }

    public static String formatName(String name) {
        String[] words = name.trim().split(" ");
        String result = "";
        for (String str : words) {
            char firstChar = str.charAt(0);
            if (!Character.isUpperCase(firstChar)) {
                result += Character.toUpperCase(firstChar) + str.substring(1) + " ";
            } else {
                result += str + " ";
            }
        }
        return result;
    }

    public static String scanPhoneBook(List<String[]> book, String findString, boolean isName) {
        String result = "";
        int i = isName ? 0 : 1;
        if (book.size()== 0) {
            return result;
        }
        for (String[] strings : book) {
            if (strings[i].equals(findString)) {
                result = strings[i^1];
                break;
            }
        }
        return result;
    }

    public static void add(List<String[]> book, String name, String number) {
        book.add(new String[] {name, number});
        System.out.printf("В книгу добавлена запись \"%s: %s\"\n", name, number);
    }

    public static void list(List<String[]> book) {
        if (book.isEmpty()){
            System.out.println("Телефонная книга пуста");
        }
        Collections.sort(book, (String[] s1, String[] s2) -> s1[0].compareTo(s2[0]));
        for (String[] strings : book) {
            System.out.println(strings[0] + ": " + strings[1]);
        }
    }
}
