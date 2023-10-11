import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BotaoSOSGUI {
    private JFrame frame;

    public BotaoSOSGUI() {
        frame = new JFrame("Sistema de Ajuda da UFS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel painelCentral = new JPanel();
        painelCentral.setBackground(Color.WHITE);
        frame.add(painelCentral, BorderLayout.CENTER);

        JPanel painelNorte = new JPanel();
        painelNorte.setLayout(new BorderLayout());

        JLabel labelSelecione = new JLabel("Selecione abaixo o local do problema");
        labelSelecione.setHorizontalAlignment(JLabel.CENTER);
        painelNorte.add(labelSelecione, BorderLayout.NORTH);

        JButton botaoAjuda = new JButton("Ajuda");
        painelNorte.add(botaoAjuda, BorderLayout.WEST);

        String[] lugares = { "Biblioteca", "Auditório", "Reitoria", "Laboratório" };
        JComboBox<String> comboLugares = new JComboBox<>(lugares);
        painelNorte.add(comboLugares, BorderLayout.CENTER);

        frame.add(painelNorte, BorderLayout.NORTH);

        RedRoundButtonSOS botaoSOS = new RedRoundButtonSOS("SOS");
        botaoSOS.setEnabled(false);

        comboLugares.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                botaoSOS.setEnabled(comboLugares.getSelectedIndex() != -1);
            }
        });

        botaoSOS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String matricula = JOptionPane.showInputDialog(frame, "Digite sua matrícula:");

                if (isNumeric(matricula) && matricula.length() == 12) {
                    String lugarProblema = (String) comboLugares.getSelectedItem();
                    String descricaoProblema = JOptionPane.showInputDialog(frame,
                            "Descreva o problema em " + lugarProblema + ":");
                    JOptionPane.showMessageDialog(frame, "SOS enviado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Matrícula inválida. Certifique-se de que seja uma matrícula válida.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton botaoMapa = new JButton("Ver Mapa");
        botaoMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File imageFile = new File("assets/testeMapa.jpeg");
                    BufferedImage image = ImageIO.read(imageFile);

                    JFrame mapaFrame = new JFrame("Mapa da UFS");
                    mapaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    mapaFrame.setSize(800, 600);
                    mapaFrame.setLocationRelativeTo(null);

                    JPanel mapaPanel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                        }
                    };

                    JButton botaoVoltar = new JButton("Voltar");
                    botaoVoltar.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            mapaFrame.dispose();
                            frame.setVisible(true);
                        }
                    });

                    mapaFrame.add(mapaPanel, BorderLayout.CENTER);
                    mapaFrame.add(botaoVoltar, BorderLayout.SOUTH);
                    mapaFrame.setVisible(true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Erro ao carregar a imagem do mapa.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoAjuda.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame ajudaFrame = new JFrame("Ajuda");
                ajudaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ajudaFrame.setSize(400, 300);
                ajudaFrame.setLocationRelativeTo(null);

                JTextArea textoAjuda = new JTextArea();
                textoAjuda.setWrapStyleWord(true);
                textoAjuda.setLineWrap(true);
                textoAjuda.setEditable(false);
                textoAjuda.setText("Bem-vindo ao Sistema de Ajuda da UFS!\n\n"
                        + "Este sistema permite que você solicite ajuda em locais específicos da universidade.\n\n"
                        + "Para solicitar ajuda:\n"
                        + "1. Selecione o local do problema no menu suspenso.\n"
                        + "2. Preencha sua matrícula e descreva o problema.\n"
                        + "3. Clique no botão 'SOS' para enviar sua solicitação de ajuda.\n\n"
                        + "Você também pode clicar no botão 'Ver Mapa' para visualizar o mapa da universidade.\n\n"
                        + "Para obter mais informações, entre em contato com o suporte.");

                JButton botaoVoltarAjuda = new JButton("Voltar");
                botaoVoltarAjuda.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        ajudaFrame.dispose();
                    }
                });

                JPanel painelAjuda = new JPanel(new BorderLayout());
                painelAjuda.add(new JScrollPane(textoAjuda), BorderLayout.CENTER);
                painelAjuda.add(botaoVoltarAjuda, BorderLayout.SOUTH);
                ajudaFrame.add(painelAjuda);

                ajudaFrame.setVisible(true);
            }
        });

        JPanel painelCentralBotoes = new JPanel();
        painelCentralBotoes.add(botaoSOS);
        painelCentralBotoes.add(botaoMapa);
        frame.add(painelCentralBotoes, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new BotaoSOSGUI();
            }
        });
    }
}

class RedRoundButtonSOS extends JButton {
    public RedRoundButtonSOS(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setPreferredSize(new Dimension(100, 100));
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(new Color(255, 102, 102));
        } else {
            g.setColor(new Color(255, 0, 0));
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(new Color(255, 0, 0));
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }
}
