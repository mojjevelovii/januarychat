import org.junit.Assert;
import org.junit.Test;

public class ArrayTasksTests {
    ArrayTasks arrayTasks = new ArrayTasks();

    //Тесты к задаче 1.
    @Test
    public void newArrTestOne() {
        Integer[] array1 = new Integer[]{1, 2, 4, 4, 2, 3, 4, 1, 7};
        Assert.assertArrayEquals(new Integer[]{1, 7}, arrayTasks.newArr(array1));
    }

    @Test(expected = RuntimeException.class)
    public void newArrTestException() {
        Integer[] array2 = new Integer[]{1, 2, 1, 5, 2, 3, 8, 1, 7}; //нет четверок
        arrayTasks.newArr(array2);
    }

    @Test
    public void newArrTestTwo() {
        Integer[] array3 = new Integer[]{1, 2, 4, 4, 2, 3, 0, 1, 7};
        Assert.assertArrayEquals(new Integer[]{2, 3, 0, 1, 7}, arrayTasks.newArr(array3));


    }

    @Test
    public void newArrTestThree() {
        Integer[] array4 = new Integer[]{1, 2, 4, 4, 2, 3, 4, 1, 4};//интересный вариант, где четверка стоит последней
        Assert.assertArrayEquals(new Integer[]{}, arrayTasks.newArr(array4));
    }

    //Тесты к задаче 2.
    @Test
    public void arrayCompositionTestOne() {
        int[] arr1 = new int[]{1, 4, 4, 1, 4};
        Assert.assertTrue(arrayTasks.arrayComposition(arr1));
    }

    @Test
    public void arrayCompositionTestTwo() {
        int[] arr2 = new int[]{1, 4, 4, 1, 1, 4, 3};
        Assert.assertFalse(arrayTasks.arrayComposition(arr2));
    }

    @Test
    public void arrayCompositionTestThree() {
        int[] arr3 = new int[]{1, 1, 1, 1, 1, 1};
        Assert.assertFalse(arrayTasks.arrayComposition(arr3));
    }

    @Test
    public void arrayCompositionTestFour() {
        int[] arr4 = new int[]{4, 4, 4, 4, 4, 4};
        Assert.assertFalse(arrayTasks.arrayComposition(arr4));
    }


}
