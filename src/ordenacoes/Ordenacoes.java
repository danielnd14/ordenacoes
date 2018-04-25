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
            trocarPosicoes(listAOrdenar, i, min, registro);
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
                    trocarPosicoes(listAOrdenar, j, j + 1, registro);
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
                        trocarPosicoes(listAOrdenar, j, j + 1, registro);
                        continuar = true;
                    }
                }
            } while (continuar);
            meio = 1 / 2;
        }
    }

    public static void quickSort(final List<Comparable> listAOrdenar, final boolean ascendente, final RegistroSaida registro) {
        int inicio = 0;
        int fim = listAOrdenar.size()-1;
        quickSort(listAOrdenar, inicio, fim, ascendente, registro);
    }

    private static void quickSort(List<Comparable> listAOrdenar, int inicio, int fim, boolean ascendente, RegistroSaida registro) {
        if (inicio < fim) {
            int posicaoPivo = separar(listAOrdenar, inicio, fim, ascendente, registro);
            quickSort(listAOrdenar, inicio, posicaoPivo - 1, ascendente, registro);
            quickSort(listAOrdenar, posicaoPivo + 1, fim, ascendente, registro);
        }
    }

    private static int separar(List<Comparable> listAOrdenar, int inicio, int fim, boolean ascendente, RegistroSaida registro) {
        Comparable pivo = listAOrdenar.get(inicio);
        int i = inicio + 1, f = fim;
        while (i <= f) {
            if (comparar(listAOrdenar.get(i), pivo, ascendente, registro) <= 0)
                i++;
            else if (comparar(pivo, listAOrdenar.get(f), ascendente, registro) < 0)
                f--;
            else {
                trocarPosicoes(listAOrdenar, i, f, registro);
                i++;
                f--;
            }
        }
        listAOrdenar.set(inicio, listAOrdenar.get(f));
        listAOrdenar.set(f, pivo);
        return f;
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

    private static void trocarPosicoes(List<Comparable> list, int posicaoA, int posicaoB, RegistroSaida registro) {
        registro.incrementaTrocas();
        Comparable elementoA = list.get(posicaoA);
        list.set(posicaoA, list.get(posicaoB));
        list.set(posicaoB, elementoA);
    }

}
