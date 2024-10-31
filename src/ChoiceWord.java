import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ChoiceWord {
    private String levelStr;
    private String secretWord = this.setWord();
    private char[] encryptedWord = new char[secretWord.length()];
    private char[] arrLetters = new char[secretWord.length()];
    private char[] errorLetters = new char[6];
    private ArrayList<Character> wroteLetters = new ArrayList<Character>();
    private int errCnt = 0;
    private int rightCnt = 0;
    private int letterCnt = 0;
    private boolean flagEncrypt = false;

    private String setWord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите уровень от 1 до 6.\n" +
                "1: 3-5 букв.\n" +
                "2: 6-8 букв.\n" +
                "3: 9-11 букв.\n" +
                "4: 12-15 букв.\n" +
                "5: 16-24 буквы.\n" +
                "6: 1-24 буквы.");
        int lvl = 0;
        boolean rightSet = false;
        while(!rightSet){
            if (scanner.hasNext()) {
                try{lvl = Integer.parseInt(scanner.nextLine());}
                catch (NumberFormatException e){
                    System.out.println("Вы ввели неверное число.");
                }
                if (lvl>0 && lvl<7){  //Проверка на правильный ввод. (Выбор уровня)
                    rightSet=true;
                }
                else{
                    System.out.println("Выберите уровень от 1 до 6.");
                }
            }
        }
        try {
            switch (lvl) {
                case 1: {
                    levelStr = "L1.txt"; //Уровень 1. Слова длиной 3-5 букв
                    System.out.println("Будет выбрано слово длиной 3-5 букв.");
                    break;
                }
                case 2: {
                    levelStr = "L2.txt"; //Уровень 2. Слова длиной 6-8 букв
                    System.out.println("Будет выбрано слово длиной 6-8 букв.");
                    break;
                }
                case 3: {
                    levelStr = "L3.txt"; //Уровень 3. Слова длиной 9-11 букв
                    System.out.println("Будет выбрано слово длиной 9-11 букв.");
                    break;
                }
                case 4: {
                    levelStr = "L4.txt"; //Уровень 4. Слова длиной 12-15 букв
                    System.out.println("Будет выбрано слово длиной 12-15 букв.");
                    break;
                }
                case 5: {
                    levelStr = "L5.txt"; //Уровень 5. Слова длиной 16-24 букв
                    System.out.println("Будет выбрано слово длиной 16-24 буквы.");
                    break;
                }
                case 6: {
                    levelStr = "ALL_L.txt"; //Уровень 6. Слова длиной 1-24 букв
                    System.out.println("Будет выбрано слово длиной 1-24 буквы.");
                    break;
                }
                default: {
                    levelStr = "ALL_L.txt"; //Уровень по умолчанию . Слова длиной 1-24 букв
                    System.out.println("Будет выбрано слово длиной 1-24 буквы.");
                    break;
                }
            }
            File path = new File(levelStr); //Библиотека с набором слов
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> listWords = new ArrayList<String>(); //Создаем пустой лист строк
            int quantityOfWords = Integer.parseInt(br.readLine()); //Количество слов в библиотеке
            int numberWord = ThreadLocalRandom.current().nextInt(0, quantityOfWords - 1); //Выбираем рандомное число
            for (int i = 0; i < quantityOfWords; i++) {
                listWords.add(br.readLine()); //заполняем лист строками(словами)
            }
            br.close();
            return listWords.get(numberWord); //Выбрали слово и вернули его
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void encryptWord() {
        for (int i = 0; i < secretWord.length(); i++) {
            encryptedWord[i] = '*'; //Зашифрованное слово
            arrLetters[i] = secretWord.charAt(i);
        }
    }

    public void showSecretWord() {
        if (errCnt < 6) {
            System.out.print("Загаданное слово: ");
            for (int i = 0; i < secretWord.length(); i++) {
                System.out.print(encryptedWord[i]); //Зашифрованное слово
            }
        }
        System.out.println();
        System.out.println("Количество ошибок: " + errCnt);
        System.out.print("Использованные буквы: "); //Вывод использованных букв
        for (int i = 0; i < wroteLetters.size(); i++) {
            System.out.print(wroteLetters.get(i));
        }
        System.out.println();
    }

    private char checkScanLetter() {
        Scanner scanner = new Scanner(System.in);
        char c = '*';
        while (true) {
            System.out.println("Введите букву.");
            if (scanner.hasNext()) { //Проверка, есть ли в буфере что-то
                String inputStr = scanner.nextLine();
                int lenghStr = inputStr.length();
                while (lenghStr == 0 || lenghStr > 1) {
                    System.out.println("Пожалуйста введите 1 букву.");
                    inputStr = scanner.nextLine();
                    lenghStr = inputStr.length();
                }
                c = inputStr.charAt(0);
                c = Character.toLowerCase(c);
                if ((c >= 'А') && (c <= 'я')) { //Введенная буква Кириллица?
                    return c;
                } else {
                    System.out.println("Ошибка,используйте кириллицу.");
                }
            }
        }
    }

    public void checkLetterInWord() {
        if (!flagEncrypt) {
            encryptWord();
            flagEncrypt = true;
            System.out.print("Загаданное слово: ");
            for (int i = 0; i < secretWord.length(); i++) {
                System.out.print(encryptedWord[i]); //Зашифрованное слово
            }
            System.out.println();
        }
        char c = checkScanLetter();
        boolean rightLetter = false;
        if (!checkInBase(c)) {
            for (int i = 0; i < secretWord.length(); i++) {
                if (c == arrLetters[i]) {
                    encryptedWord[i] = c; //Символ есть в слове! Записать в зашифрованное слово
                    rightCnt++;
                    rightLetter = true;
                }
            }
            if (!rightLetter) {
                errorLetters[errCnt] = c; //Символа нет в слове. Добавить в базу ошибок
                errCnt++; //Увеличить счетчик ошибок
            }
        }
    }

    private boolean checkInBase(char c) {
        boolean haveLetter = false;
        int letCnt = letterCnt;
        for (int i = 0; i < letCnt; i++) {
            if (c == wroteLetters.get(i)) {
                haveLetter = true; //Буква есть в базе. Ранее её уже вводили
            }
        }
        if (haveLetter == false) {
            wroteLetters.add(c); //Записали символ в массив введенных букв
            letterCnt++; //Увеличили счетчик введенных букв
        }

        return haveLetter;
    }

    public boolean checkEndGame() {
        if (rightCnt == encryptedWord.length) {
            System.out.println("Вы угадали слово! Поздравляем!");
            return true;
        } else if (errCnt == 6) {
            System.out.print("Вы не угадали слово.\n" +
                    "Загаданное слово: " + secretWord + "\r\n");
            return true;
        }
        return false;
    }

    public int getErrCnt() {
        return errCnt;
    }
}
