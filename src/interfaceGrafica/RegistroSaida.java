package interfaceGrafica;

public class RegistroSaida {
    Integer numItens = 0;
    String metodo;
    Integer comparacoes = 0;
    Integer numTrocas = 0;
    String complexidade;
    String otimo;
    String estavel;
    String inPlace;
    Long tempoGasto = 0L;

    public void incrementaTrocas() {
        this.numTrocas++;
    }

    public void incrementaComparacoes(){
        this.comparacoes++;
    }
}
