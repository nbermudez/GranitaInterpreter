/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Tests;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class TestQuicksort {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ___();
        quicksort(0, arraySize - 1);
        print_array();
    }
    static final int arraySize = 5;
    static int array[] = new int[arraySize];

    static void quicksort(int izq, int der) {
        if (izq < der) {
            int k;
            k = particionar(izq, der);
            quicksort(izq, k);
            quicksort(k + 1, der);
        }
    }

    static int particionar(int izq, int der) {
        int p;
        int i, j;
        p = array[izq];
        i = izq;
        j = der;
        while (i < j) {
            while (array[i] > p && i < der) {
                i = i + 1;
            }
            while (array[j] <= p && j > izq) {
                j = j - 1;
            }

            if (i == izq && j == i) {
                quicksort(izq + 1, der);
            }
            if (i == der && j == i) {
                quicksort(izq, der - 1);
            }

            if (i < j) {
                swap(i, j);
            }
        }
        return j;
    }

    static void swap(int i, int j) {
        int aux;
        aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    }

    static void ___() {
        int i;
        for (i = 0; i < arraySize; i = i + 1) {
            array[i] = i;
        }
    }

    static void print_array() {
        int i;
        for (i = 0; i < arraySize; i = i + 1) {
            System.out.println(array[i]);
        }
    }
}
