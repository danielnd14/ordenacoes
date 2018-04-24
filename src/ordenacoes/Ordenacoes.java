package ordenacoes;

import interfaceGrafica.RegistroSaida;

import java.util.ArrayList;
import java.util.List;


public class Ordenacoes {

    public static void insertionSort(final List<Comparable> ListAOrdenar, boolean ascendente, final RegistroSaida registro) {
        Comparable elemento;

        for (int i = 1; i < ListAOrdenar.size(); i++) {
            elemento = ListAOrdenar.get(i);
            int j = i - 1;

            while (j >= 0 && comparar(ListAOrdenar.get(j), elemento, ascendente, registro) > 0) {

                ListAOrdenar.set(j + 1, ListAOrdenar.get(j));
                j = j - 1;
            }

            ListAOrdenar.set(j + 1, elemento);

        }


    }


    public static void selectionSort(final List<Comparable> listAOrdenar, final boolean ascendente, final RegistroSaida registro) {

        int min;
        Comparable elementoA;
        Comparable elementoB;
        for (int i = 0; i < listAOrdenar.size() - 1; i++) {
            min = i;
            for (int j = (i + 1); j < listAOrdenar.size(); j++) {
                elementoA = listAOrdenar.get(j);
                elementoB = listAOrdenar.get(min);
                if (comparar(elementoA, elementoB, ascendente, registro) < 0) {
                    min = j;
                }
            }
            registro.incrementaTrocas();
            trocarPosicoes(listAOrdenar, i, min);
        }


    }

    public static void bubleSort(final List<Comparable> listAOrdenar, final boolean ascendente, final RegistroSaida registro) {
        Comparable elementoA;
        Comparable elementoB;
        for (int i = listAOrdenar.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                elementoA = listAOrdenar.get(j);
                elementoB = listAOrdenar.get(j + 1);
                if (comparar(elementoA, elementoB, ascendente, registro) > 0) {
                    registro.incrementaTrocas();
                    trocarPosicoes(listAOrdenar, j, j + 1);
                }
            }
        }

    }


    public static void shellSort(final List<Comparable> listAOrdenar, final boolean ascendente, final RegistroSaida registro) {
        int meio = listAOrdenar.size() / 2;
        boolean continuar;
        Comparable elementoA;
        Comparable elementoB;
        while (meio != 0) {
            do {
                continuar = false;
                for (int j = 0; j < listAOrdenar.size() - 1; j++) {
                    elementoA = listAOrdenar.get(j);
                    elementoB = listAOrdenar.get(j + 1);
                    if (comparar(elementoA, elementoB, ascendente, registro) > 0) {
                        registro.incrementaTrocas();
                        trocarPosicoes(listAOrdenar, j, j + 1);
                        continuar = true;
                    }
                }
            } while (continuar);
            meio = 1 / 2;
        }
    }

    public static void quickSort(final List<Comparable> listAOrdenar, final boolean ascendente, final RegistroSaida registro) {
        int inicio = 0;
        int fim = listAOrdenar.size();
        quickSort(listAOrdenar, inicio, fim, ascendente, registro);
    }

    public static void quickSort(final List<Comparable> listAOrdenar, int inicio, int fim, final boolean ascendente, final RegistroSaida registro) {
        int i, j;

        i = inicio;
        j = fim - 1;
        Comparable elementoI;
        Comparable elementoJ;
        Comparable elementoM = listAOrdenar.get(((inicio + fim) / 2));

        while (i <= j) {
            elementoI = listAOrdenar.get(i);
            elementoJ = listAOrdenar.get(j);

            while (comparar(elementoI, elementoM, ascendente, registro) < 0 && i < fim) {
                i++;
                elementoI = listAOrdenar.get(i);
            }
            while (comparar(elementoJ, elementoM, ascendente, registro) > 0 && j > inicio) {
                j--;
                elementoJ = listAOrdenar.get(j);
            }

            if (i <= j) {
                registro.incrementaTrocas();
                trocarPosicoes(listAOrdenar, i, j);
                i++;
                j--;
            }

            if (j > inicio) {
                quickSort(listAOrdenar, inicio, j + 1, ascendente, registro);
            }
            if (i < fim) {
                quickSort(listAOrdenar, i, fim, ascendente, registro);

            }
        }
    }

    public static void mergeSort(final List<Comparable> listAOrdenar, final boolean ascendente, final RegistroSaida registro) {
        final int inicio = 0;
        final int fim = listAOrdenar.size() - 1;
        mergeSort(listAOrdenar, inicio, fim, ascendente, registro);

    }

    public static void mergeSort(final List<Comparable> listAOrdenar, int inicio, int fim, boolean ascendente, final RegistroSaida registro) {
        if (listAOrdenar != null && inicio < fim && inicio >= 0 &&
                fim < listAOrdenar.size() && !listAOrdenar.isEmpty()) {

            int meio = (fim + inicio) / 2;
            mergeSort(listAOrdenar, inicio, meio, ascendente, registro);
            mergeSort(listAOrdenar, meio + 1, fim, ascendente, registro);
            merge(listAOrdenar, inicio, meio, fim, ascendente, registro);
        }
    }

    private static void merge(final List<Comparable> listAOrdenar, int inicio, int meio, int fim, final boolean ascendente, final RegistroSaida registro) {


        final List<Comparable> listAux = new ArrayList<>(listAOrdenar);
        for (int i = inicio; i <= fim; i++) {
            listAux.set(i, listAOrdenar.get(i));
        }


        int i = inicio, j = meio + 1, k = inicio;
        Comparable elementoA;
        Comparable elementoB;
        while (i <= meio && j <= fim) {
            elementoA = listAux.get(i);
            elementoB = listAux.get(j);
            if (comparar(elementoA, elementoB, ascendente, registro) < 0) {
                listAOrdenar.set(k, elementoA);
                i++;
            } else {
                listAOrdenar.set(k, elementoB);
                j++;
            }

            k++;
        }

        while (i <= meio) {
            listAOrdenar.set(k, listAux.get(i));
            i++;
            k++;
        }
        while (j <= fim) {
            listAOrdenar.set(k, listAux.get(j));
            j++;
            k++;
        }
    }


    private static int comparar(final Comparable elementoA, final Comparable elementoB, boolean ascendente, final RegistroSaida registro) {
        registro.incrementaComparacoes();
        if (ascendente) {
            return elementoA.compareTo(elementoB);
        }
        return elementoA.compareTo(elementoB) * -1;
    }

    private static void trocarPosicoes(List<Comparable> list, int posicaoA, int posicaoB) {
        Comparable elementoA = list.get(posicaoA);
        list.set(posicaoA, list.get(posicaoB));
        list.set(posicaoB, elementoA);
    }

}
