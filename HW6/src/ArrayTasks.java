import java.util.Arrays;


public class ArrayTasks {

/*1. Написать метод, которому в качестве аргумента передается не пустой
одномерный целочисленный массив. Метод должен вернуть новый массив, который
получен путем вытаскивания из исходного массива элементов,
идущих после последней четверки. Входной массив должен содержать хотя бы одну
четверку, иначе в методе необходимо выбросить RuntimeException.
Написать набор тестов для этого метода (по 3-4 варианта входных данных).
Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
*/

    public Integer[] newArr(Integer[] numbers) {
        int i = Arrays.asList(numbers).lastIndexOf(4);
        if (i == -1) {
            throw new RuntimeException();
        }
        return Arrays.copyOfRange(numbers, i + 1, numbers.length);
    }


/*2.Написать метод, который проверяет состав массива из чисел 1 и 4.
Если в нем нет хоть одной четверки или единицы, то метод вернет false;
Написать набор тестов для этого метода (по 3-4 варианта входных данных).
[ 1 1 1 4 4 1 4 4 ] -> true
[ 1 1 1 1 1 1 ] -> false
[ 4 4 4 4 ] -> false
[ 1 4 4 1 1 4 3 ] -> false
*/

    public boolean arrayComposition(int[] arr) {
        boolean hasFour = false;
        boolean hasOne = false;

        for (int element : arr) {
            if (element != 4 && element != 1) {
                return false;
            }
            if (!hasFour) {
                hasFour = element == 4;
            }
            if (!hasOne) {
                hasOne = element == 1;
            }
        }
        return hasFour && hasOne;
    }
}
