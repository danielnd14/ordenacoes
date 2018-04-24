package interfaceGrafica;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import ordenacoes.Ordenacoes;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * @author daniel
 */
@SuppressWarnings("unchecked")
public class View extends javax.swing.JFrame {
    private static javax.swing.JTable tabelaDadosSaida;


    public View() {
        setLookAndFeel();
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private static void setLookAndFeel() {
        Arrays
                .stream(UIManager.getInstalledLookAndFeels())
                .parallel()
                .forEach(info -> {
                    if ("Nimbus".equals(info.getName())) {
                        try {
                            UIManager.setLookAndFeel(info.getClassName());
                        } catch (ClassNotFoundException | InstantiationException |
                                UnsupportedLookAndFeelException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private static void ordenar(final String metodo, final JTable tabelaReferencia, final JTable tabelaDestino, boolean ascendente) {
        if (tabelaReferencia.getRowCount() > 0) {
            List numeros = new ArrayList<Integer>();
            for (int i = 0; i < tabelaReferencia.getRowCount(); i++) {
                numeros.add(tabelaReferencia.getValueAt(i, 0));
            }
            RegistroSaida registro = new RegistroSaida();

            registro.numItens = numeros.size();
            long tempoGasto;

            switch (metodo) {
                case "Quick sort": {
                    tempoGasto = System.currentTimeMillis();
                    Ordenacoes.quickSort(numeros, ascendente, registro);
                    registro.tempoGasto = System.currentTimeMillis() - tempoGasto;
                    registro.metodo = "Quick sort";
                    registro.inPlace = "N";
                    registro.estavel = "N";
                    registro.complexidade = "O(n²)";
                    registro.otimo = "N";
                    inserirLinhaTabelaSaida(registro);
                }
                break;
                case "Merge sort": {
                    tempoGasto = System.currentTimeMillis();
                    Ordenacoes.mergeSort(numeros, ascendente, registro);
                    registro.tempoGasto = System.currentTimeMillis() - tempoGasto;
                    registro.metodo = "Merge sort";
                    registro.inPlace = "N";
                    registro.estavel = "S";
                    registro.complexidade = "Teta(nLog(n))";
                    registro.otimo = "S";
                    inserirLinhaTabelaSaida(registro);
                }
                break;
                case "Bublle sort": {
                    tempoGasto = System.currentTimeMillis();
                    Ordenacoes.bubleSort(numeros, ascendente, registro);
                    registro.tempoGasto = System.currentTimeMillis() - tempoGasto;
                    registro.metodo = "Bublle sort";
                    registro.inPlace = "S";
                    registro.estavel = "S";
                    registro.complexidade = "O(n²)";
                    registro.otimo = "N";
                    inserirLinhaTabelaSaida(registro);
                }
                break;
                case "Selection sort": {
                    tempoGasto = System.currentTimeMillis();
                    Ordenacoes.selectionSort(numeros, ascendente, registro);
                    registro.tempoGasto = System.currentTimeMillis() - tempoGasto;
                    registro.metodo = "Selection sort";
                    registro.inPlace = "S";
                    registro.estavel = "N";
                    registro.complexidade = "O(n²)";
                    registro.otimo = "N";
                    inserirLinhaTabelaSaida(registro);
                }
                break;
                case "Shell sort": {
                    tempoGasto = System.currentTimeMillis();
                    Ordenacoes.shellSort(numeros, ascendente, registro);
                    registro.tempoGasto = System.currentTimeMillis() - tempoGasto;
                    registro.metodo = "Shell sort";
                    registro.inPlace = "S";
                    registro.estavel = "N";
                    registro.complexidade = "O(n²)";
                    registro.otimo = "N";
                    inserirLinhaTabelaSaida(registro);
                }
                break;
                case "Insertion sort": {
                    tempoGasto = System.currentTimeMillis();
                    Ordenacoes.insertionSort(numeros, ascendente, registro);
                    registro.tempoGasto = System.currentTimeMillis() - tempoGasto;
                    registro.metodo = "Insertion sort";
                    registro.inPlace = "S";
                    registro.estavel = "S";
                    registro.complexidade = "O(n²)";
                    registro.otimo = "N";
                    inserirLinhaTabelaSaida(registro);
                }
                break;
            }
            preencherTabelaOrdenada(numeros, tabelaDestino);
        }

    }

    private static void preencherTabelaOrdenada(final List<Integer> numeros, final JTable tabela) {
        tabela.removeAll();

        DefaultTableModel model = getModelTabEntradaDestino("Numeros Ordenados");

        tabela.setModel(model);

        SwingUtilities.invokeLater(() -> numeros.stream().map(numero -> new Object[]{numero}).forEach(model::addRow));
    }

    private static void preencherTabelaEntradaAutomaticamente(final JTable tabela, int quantidade, boolean aleatoriamente) {
        tabela.removeAll();
        DefaultTableModel model = getModelTabEntradaDestino("Numeros Obtidos");
        Random rd = new Random();

        SwingUtilities.invokeLater(() -> {
            int val;
            for (int i = quantidade; i > 0; i--) {
                val = i;
                if (aleatoriamente) {
                    val = rd.nextInt(i);
                }
                model.addRow(new Object[]{val});
            }
            tabela.setModel(model);
        });

    }

    private static void preencherTabelaFromFile(final JTable tabelaEntrada, final Path path) {
        try {
            tabelaEntrada.removeAll();
            DefaultTableModel model = getModelTabEntradaDestino("Numeros Obtidos");
            final Stream<String> stringStream = Files.lines(path);
            SwingUtilities.invokeLater(() -> {
                stringStream.forEach(val ->
                        model.addRow(new Object[]{Integer.valueOf(val)})
                );
                tabelaEntrada.setModel(model);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static DefaultTableModel getModelTabEntradaDestino(final String titulo) {
        return new DefaultTableModel(new Object[][]{
        }, new String[]{
                titulo}

        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false};

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
    }

    private static void inserirLinhaTabelaSaida(final RegistroSaida registroSaida) {
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel model = (DefaultTableModel) tabelaDadosSaida.getModel();
            model.addRow(new Object[]{registroSaida.numItens,
                    registroSaida.metodo,
                    registroSaida.comparacoes,
                    registroSaida.numTrocas,
                    registroSaida.complexidade,
                    registroSaida.otimo,
                    registroSaida.estavel,
                    registroSaida.inPlace,
                    registroSaida.tempoGasto});
        });

    }


    private void initComponents() {

        JButton btnAbrir = new JButton();
        JButton btnEntradaSequencial = new JButton();
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable tabelaEntrada = new JTable();
        JScrollPane jScrollPane2 = new JScrollPane();
        tabelaDadosSaida = new JTable();
        JButton btnEntradaAleatoria = new JButton();
        JButton btnOrdenar = new JButton();
        JScrollPane jScrollPane3 = new JScrollPane();
        JTable tabelaOrdenada = new JTable();
        JComboBox<String> comboMetodo = new JComboBox<>();
        JComboBox<String> comboQuantidade = new JComboBox<>();
        JComboBox<String> comboOrdem = new javax.swing.JComboBox<>();
        comboMetodo.setModel(new DefaultComboBoxModel<>(new String[]{"Quick sort", "Merge sort", "Bublle sort", "Selection sort", "Shell sort", "Insertion sort"}));
        comboQuantidade.setModel(new DefaultComboBoxModel<>(new String[]{"500", "1000", "10000", "50000"}));
        comboOrdem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Ascendente", "Descendente"}));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnAbrir.setText("Abrir Arquivo");
        btnEntradaSequencial.setText("Gerar Entrada sequencial");
        btnEntradaAleatoria.setText("Gerar Entrada aleatoria");
        btnOrdenar.setText("Ordenar!");

        btnAbrir.addActionListener((actionEvent) -> {
            final JFileChooser fchooser = new JFileChooser();

            fchooser.setFileFilter(new FileNameExtensionFilter("Plain Text", "txt"));

            if (fchooser.showOpenDialog(fchooser) == JFileChooser.APPROVE_OPTION) {
                preencherTabelaFromFile(tabelaEntrada, fchooser.getSelectedFile().toPath());
            }
        });

        btnOrdenar.addActionListener((actionEvent) -> {
            String metodo = comboMetodo.getItemAt(comboMetodo.getSelectedIndex());
            String asc = comboOrdem.getItemAt(comboOrdem.getSelectedIndex());
            boolean ascendente = asc.equals("Ascendente");
            ordenar(metodo, tabelaEntrada, tabelaOrdenada, ascendente);
        });


        btnEntradaAleatoria.addActionListener((actionEvent) -> {
            int quantidade = Integer.valueOf(comboQuantidade.getItemAt(comboQuantidade.getSelectedIndex()));
            preencherTabelaEntradaAutomaticamente(tabelaEntrada, quantidade, true);
        });

        btnEntradaSequencial.addActionListener((actionEvent) -> {
            int quantidade = Integer.valueOf(comboQuantidade.getItemAt(comboQuantidade.getSelectedIndex()));
            preencherTabelaEntradaAutomaticamente(tabelaEntrada, quantidade, false);
        });

        tabelaEntrada.setModel(new DefaultTableModel(
                new Integer[][]{
                },
                new String[]{
                        "Numeros Obtidos"
                }
        ));
        jScrollPane1.setViewportView(tabelaEntrada);

        tabelaDadosSaida.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Nº de Itens", "Metodo", "Comparaçoes", "Nº Trocas", "Complexidade", "Otimo", "Estavel", "In Place", "Tempo (ms)"
                }
        ));
        jScrollPane2.setViewportView(tabelaDadosSaida);

        tabelaOrdenada.setModel(new DefaultTableModel(
                new Object[][]{
                },
                new String[]{
                        "Numeros Ordenados"
                }
        ));
        jScrollPane3.setViewportView(tabelaOrdenada);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane2)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(btnAbrir)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(comboQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnEntradaSequencial)
                                                                .addGap(12, 12, 12)
                                                                .addComponent(btnEntradaAleatoria))
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(comboMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(comboOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(btnOrdenar))
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnAbrir)
                                        .addComponent(btnEntradaAleatoria)
                                        .addComponent(btnOrdenar)
                                        .addComponent(comboMetodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnEntradaSequencial)
                                        .addComponent(comboOrdem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
        this.setResizable(false);
    }
}