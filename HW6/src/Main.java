
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Integer[] array = new Integer[]{1, 2, 4, 4, 2, 3, 4, 1, 7};
        ArrayTasks at = new ArrayTasks();

        System.out.println(Arrays.toString(array));
        System.out.println(Arrays.toString(at.newArr(array)));

        System.out.println(at.arrayComposition(new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}));
        System.out.println(at.arrayComposition(new int[]{1, 4, 4, 1, 4}));
    }


}
