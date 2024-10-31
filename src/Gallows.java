import java.util.ArrayList;

public class Gallows {
    private int numError = 0;
    private ArrayList createClearTree() {
        ArrayList<String> clearTree = new ArrayList<String>();
        clearTree.add("     ----------     ");
        clearTree.add("     |        |     ");
        clearTree.add("     |              ");
        clearTree.add("     |              ");
        clearTree.add("     |              ");
        clearTree.add("     |              ");
        clearTree.add("  ___|___           ");
        return clearTree;
    }

    private ArrayList stringForError() {
        ArrayList<String> human = new ArrayList<String>();
        human.add("     |        0     "); //1 ошибка
        human.add("     |        |     "); //2 ошибка
        human.add("     |      --|     "); //3 ошибка
        human.add("     |      --|--   "); //4 ошибка
        human.add("     |       /      "); //5 ошибка
        human.add("     |       / \\   "); //6 ошибка
        return human;
    }

    public void setNumError(int numError) {
        this.numError = numError;
    }

    public void showTree(int numberError) {
        ArrayList<String> tree = createClearTree();
        ArrayList<String> human = stringForError();
        setNumError(numberError);

        for (int i = 1; i <= numError; i++) { //Изменить дерево в зависимости от кол-ва ошибок
            if ((i == 3)||(i == 4)) {
                tree.set(3, human.get(i-1));
            } else if(i<3){
                tree.set(1 + i, human.get(i-1));
            } else if (i>4) {
                tree.set(4, human.get(i-1));
            }
        }
        for (int i = 0; i < tree.size(); i++) {
            System.out.println(tree.get(i)); //Вывести дерево в консоль
        }
    }


}
