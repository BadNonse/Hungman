import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("Начать новую игру? [Да] или [Нет]?");
            Scanner scn = new Scanner(System.in);
            String str = scn.nextLine();
            if (str.equalsIgnoreCase("Да")) {
                gameStart();
            } else if (str.equalsIgnoreCase("Нет")) {
                System.out.println("Вы отказались начать новую игру. Удачи!");
                scn.close();
                break;
            } else {
                System.out.println("Не верный ввод. Повторите ответ.");
            }
        }
    }
    public static void gameStart( ){
        ChoiceWord word = new ChoiceWord();
        Gallows tree = new Gallows();
        while (!word.checkEndGame()){
            word.checkLetterInWord();
            tree.showTree(word.getErrCnt());
            word.showSecretWord();
        }

    }
}
