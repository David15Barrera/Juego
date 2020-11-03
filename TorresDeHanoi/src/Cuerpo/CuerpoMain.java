package Cuerpo;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import HanoiArchivos.TorresDeHanoi;
import PackTorres.Anillo;
import PackTorres.Torre;

/**
 * @author dav
 */
public class CuerpoMain extends JPanel implements ActionListener, MouseListener {

   TorresDeHanoi hanoi;

    int aros;
    int numeroDePasos;
    int arosJuego;
    
    //torres
    Torre torre1;
    Torre torre2;
    Torre torre3;
    JButton iniciar;
    JComboBox numeroAros;
    JLabel pasosEchos;
    JLabel labelPasos;
    JLabel numeroDeAros;
    JLabel nombreTorre1;
    JLabel nombreTorre2;
    JLabel nombreTorre3;
    JLabel inicio;

    JButton resolver;

    //para mover el aro
    boolean seleccionado;
    Anillo aroMover;

    /**
     * Generar el cuerpo de  trabajo de las torres
     */
    public CuerpoMain() throws IOException {
        numeroDePasos = 0;
        aros = 3;
        aroMover = null;
        seleccionado = false;
        hanoi = new TorresDeHanoi();
        hanoi.hanoi(aros, 1, 2, 3);
        arosJuego=3;
        
        this.setBackground(Color.ORANGE);

        this.setLayout(null);

        //label que muestra el numero de pasos optimos para resolver el juego
        labelPasos = new JLabel("Minimo de Movimientos: " + hanoi.getContador());
        labelPasos.setFont(new Font("Serif", Font.BOLD, 12));
        labelPasos.setBounds(500, 400, 220, 10);
        this.add(labelPasos);

        //Label que muestra el numero de pasos del jugador
        pasosEchos = new JLabel("Numero de Pasos:");
        pasosEchos.setFont(new Font("Serif", Font.BOLD, 12));
        pasosEchos.setBounds(500, 420, 180, 15);
        this.add(pasosEchos);
        //Label que muestra inicio
        inicio = new JLabel("El juego de los discos: La Torre de Hanoi");
        inicio.setFont(new Font("Serif", Font.BOLD, 14));
        inicio.setBounds(310, 30, 350, 15);
        this.add(inicio);
        
        torre1 = new Torre();
        torre1.setBounds(150, 60, 220, 300);
        torre1.addMouseListener(this);
        agregarAros(3);
        this.add(torre1);

        torre2 = new Torre();
        torre2.setBounds(370, 60, 220, 300);
        torre2.addMouseListener(this);
        this.add(torre2);


        torre3 = new Torre();
        torre3.setBounds(590, 60, 220, 300);
        torre3.addMouseListener(this);
        this.add(torre3);

        //Numero de discos
        numeroDeAros = new JLabel("Numero de discos:");
        numeroDeAros.setFont(new Font("Serif", Font.BOLD, 12));
        numeroDeAros.setBounds(10, 30, 150, 50);
        this.add(numeroDeAros);

        iniciar = new JButton("Iniciar");
        iniciar.setBounds(400, 400, 90, 30);
        iniciar.addActionListener(this);
        this.add(iniciar);

        numeroAros = new JComboBox();
        //llenar el jcomboBox
        for (int i = 2; i <= 14; i++) {
            numeroAros.addItem(i);
        }
        numeroAros.setBounds(10, 70, 90, 30);
        this.add(numeroAros);
    }

    /**
     * Agrega una cantidad de discos
     *
     * @param n, nuemro de discos que se van a agregar en el cuerpo principal
     */
    public void agregarAros(int n) {

        String nombreAro = "aro";

        Anillo aro = new Anillo();
        aro.setBounds(90, 250, 45, 20);
        aro.addMouseListener((MouseListener) this);
        torre1.add(aro);
        torre1.updateUI();

        for (int i = 1; i <= n - 1; i++) {
            torre1.add(new Anillo());
            torre1.getComponent(i).addMouseListener(this);
        }

        organizar(n);

        torre1.updateUI();

    }

    /**
     * Método que queden ordenados del mas grande hasta el mas pequeño
     * @param n, el numero de aros que hay en las torres de hanoi
     */
    public void organizar(int n) {
        if (n >= 0) {
            for (int j = 1; j <= n - 1; j++) {

                //panel Anterior
                JPanel anterior = (JPanel) torre1.getComponent(j - 1);
                //posiciones y tamaño del aro anterior
                int x = anterior.getX();
                int y = anterior.getY();
                int w = anterior.getWidth();
                int h = anterior.getHeight();

                //Panel que se va a modificar
                JPanel aroA = (JPanel) torre1.getComponent(j);
                aroA.setBounds(x, y - h, w, h);
                anterior.setBounds(x - 10, y, w + 20, h);
                torre1.setComponentZOrder(aroA, j);
                torre1.setComponentZOrder(anterior, j - 1);

            }
            organizar(n - 1);
        }

    }

    /**
     * Metodo del evento del boton iniciar
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniciar) {
            try {
                numeroDePasos = 0;
                int n = (int) numeroAros.getSelectedItem();
                arosJuego=n;
                hanoi.setContador(0);
                aros = n;
                hanoi.setContador(0);
                //calcula la solucion optima para resolver el juego
                hanoi.hanoi(n, 1, 2, 3);
                

                int pasosMinimos = hanoi.getContador();

                //actualiza el Label minimo de pasos
                labelPasos.setText("Minimo de Movimientos: " + pasosMinimos);

                //eliminar los aros de las torres
                torre1.removeAll();
                torre2.removeAll();
                torre3.removeAll();

                //redibujar las torres
                torre1.updateUI();
                torre2.updateUI();
                torre3.updateUI();

                hanoi.setContador(0);
                pasosEchos.setText("Numero de pasos: ");
                agregarAros(n);

            } catch (IOException ex) {
                Logger.getLogger(CuerpoMain.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (e.getSource() == resolver) {
            JFileChooser file = new JFileChooser();
            file.showSaveDialog(this);
            File guarda = file.getSelectedFile();
            if(guarda!=null){
                try {
                    
                    hanoi.hanoi(arosJuego, 1, 2, 3);
                    hanoi.guardarSolucion(guarda);
                } catch (IOException ex) {
                    Logger.getLogger(CuerpoMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Metodo que muestra las acciones cuando doy clicks entre las torres para
     * poder mover un aro
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        //mover de la torre 1 a otra torre
        if (torre1.getComponentCount() > 0) {
            if (e.getSource() == torre1.getComponent(torre1.getComponentCount() - 1) && seleccionado == false) {
                //aro que voy a mover
                aroMover = (Anillo) torre1.getComponent(torre1.getComponentCount() - 1);
                seleccionado = true;
                //elimino el aro de la torre
                torre1.remove(torre1.getComponentCount() - 1);
                torre1.updateUI();
            }
        }
        if (torre2.getComponentCount() > 0) {
            if (e.getSource() == torre2.getComponent(torre2.getComponentCount() - 1) && seleccionado == false) {

                aroMover = (Anillo) torre2.getComponent(torre2.getComponentCount() - 1);
                seleccionado = true;
                torre2.remove(torre2.getComponentCount() - 1);
                torre2.updateUI();
            }
        }

        if (torre3.getComponentCount() > 0) {
            if (e.getSource() == torre3.getComponent(torre3.getComponentCount() - 1) && seleccionado == false) {
                aroMover = (Anillo) torre3.getComponent(torre3.getComponentCount() - 1);
                seleccionado = true;
                torre3.remove(torre3.getComponentCount() - 1);
                torre3.updateUI();
            }
        }

        if (e.getSource() == torre1 && seleccionado == true) {
            if (aroMover != null) {
                //guardar el ancho y alto para acomodar
                int x = aroMover.getX();//posicion en x
                int h = aroMover.getHeight();//altura
                int w = aroMover.getWidth();//anchura
                if (torre1.getComponentCount() == 0) {

                    aroMover.setBounds(x, 250, w, h);

                    torre1.add(aroMover);
                    torre1.updateUI();
                    numeroDePasos++;
                    aroMover = null;
                    seleccionado = false;

                } else {

                    //ultimo aro que hay en la torre
                    Anillo aroPresente = (Anillo) torre1.getComponent(torre1.getComponentCount() - 1);
                    //si retorna true
                    if (verificar(aroPresente, aroMover)) {

                        aroMover.setBounds(x, 250 - (20 * torre1.getComponentCount() - 1), w, 20);
                        torre1.add(aroMover);
                        torre1.updateUI();
                        numeroDePasos++;
                        aroMover = null;
                        seleccionado = false;

                    } else {
                        JOptionPane.showMessageDialog(null, "Movimiento inavlido");
                    }
                }
            }
        }
        if (e.getSource() == torre2 && seleccionado == true) {
            if (aroMover != null) {
                //guardar el ancho y alto para acomodar
                int x = aroMover.getX();//posicion en x
                int w = aroMover.getWidth();//anchura
                if (torre2.getComponentCount() == 0) {
                    aroMover.setBounds(x, 250, w, 20);

                    torre2.add(aroMover);
                    torre2.updateUI();
                    numeroDePasos++;
                    pasosEchos.setText("Numero de pasos: " + numeroDePasos);

                    aroMover = null;
                    seleccionado = false;

                } else {
                    Anillo aroPresente = (Anillo) torre2.getComponent(torre2.getComponentCount() - 1);

                    if (verificar(aroPresente, aroMover)) {

                        aroMover.setBounds(x, 250 - (20 * torre2.getComponentCount() - 1), w, 20);
                        torre2.add(aroMover);
                        torre2.updateUI();

                        numeroDePasos++;

                        pasosEchos.setText("Numero de pasos: " + numeroDePasos);

                        aroMover = null;
                        seleccionado = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Movimiento invalido");
                    }
                }
            }
        }

        //En la torre 3 una vez echo click en el disco de la otra torre
        if (e.getSource() == torre3 && seleccionado == true) {
            if (aroMover != null) {

                //guardar el ancho y alto para acomodar
                int x = aroMover.getX();//posicion en x
                int h = aroMover.getHeight();//altura
                int w = aroMover.getWidth();//anchura
                if (torre3.getComponentCount() == 0) {

                    //acomodar el aro que entra
                    aroMover.setBounds(x, 250, w, h);

                    torre3.add(aroMover);
                    torre3.updateUI();
                    aroMover = null;
                    numeroDePasos++;
                    pasosEchos.setText("Numero de pasos: " + numeroDePasos);
                    seleccionado = false;

                } else {

                    Anillo aroPresente = (Anillo) torre3.getComponent(torre3.getComponentCount() - 1);
                    if (verificar(aroPresente, aroMover)) {

                        aroMover.setBounds(x, 250 - (20 * torre3.getComponentCount() - 1), w, 20);
                        torre3.add(aroMover);
                        torre3.updateUI();

                        numeroDePasos++;

                        //Actualiza el numero de pasos que hace el jugador
                        pasosEchos.setText("Numero de pasos: " + numeroDePasos);

                        aroMover = null;
                        seleccionado = false;
                    } else {
                        JOptionPane.showMessageDialog(null, "Movimiento no aceptado");
                    }
                }
            }
        }

        if (e.getSource() == torre3) {
              if (hanoi.verificarFinalJuego(aros, torre3.getComponentCount()) == true) {
                 JOptionPane.showMessageDialog(null, "Finalizo el juego con  " + numeroDePasos +" movimientos");                  

            }
        }
    }
    public boolean verificar(Anillo aroPresente, Anillo aroAMover) {
        int w = aroPresente.getWidth();
        int w2 = aroAMover.getWidth();

        if(w > w2) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }



}
