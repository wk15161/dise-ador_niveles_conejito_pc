package crearnivelesconejitopc;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

/**

* Diseñador básico de niveles para el juego.
*
* IMPORTANTE:
* Este programa NO modifica GameDesign.java.
*
* Permite:
* * Cargar niveles existentes desde GameDesign.
* * Crear mapas nuevos.
* * Seleccionar tiles.
* * Pintar con clic izquierdo.
* * Borrar con clic derecho.
* * Pintar arrastrando.
* * Zoom.
* * Cuadrícula.
* * Exportar int[][].
* * Exportar el método getNivel_X().
    */
    public class CrearNivelesConejitoPC extends JFrame {

  // =========================================================
  // CONFIGURACION
  // =========================================================

  private static final int TILE_SIZE = 16;

  private static final int TILE_VACIO = 0;

  private static final int ZOOM_MIN = 1;
  private static final int ZOOM_MAX = 5;

  // =========================================================
  // GAME DESIGN
  // =========================================================

  private GameDesign gameDesign;

  // =========================================================
  // MAPA ACTUAL
  // =========================================================

  private int[][] mapa;

  private String nombreNivelActual = "Nivel 1";

  private int numeroNivelActual = 1;

  private boolean nivelExistente = true;

  // =========================================================
  // TILESET
  // =========================================================

  private BufferedImage tileset;

  private String nombreTileset = "normal";

  private int tileSeleccionado = 1;

  // =========================================================
  // INTERFAZ
  // =========================================================

  private MapaPanel mapaPanel;

  private TilesetPanel tilesetPanel;

  private JComboBox<String> comboNivel;

  private JComboBox<String> comboTileset;

  private JLabel labelTile;

  private JLabel labelDimension;

  private JLabel labelEstado;

  private JSpinner spinnerZoom;

  private JRadioButton radioPintar;

  private JRadioButton radioBorrar;

  // =========================================================
  // CONSTRUCTOR
  // =========================================================

  public CrearNivelesConejitoPC() {

  
   super("Diseñador de Niveles - Juego");

   gameDesign = new GameDesign();

   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

   construirInterfaz();

   cargarNivelExistente(1);

   setSize(1250, 800);

   setLocationRelativeTo(null);
  

  }

  // =========================================================
  // INTERFAZ PRINCIPAL
  // =========================================================

  private void construirInterfaz() {

  
   setLayout(new BorderLayout());

   construirBarraSuperior();

   construirCentro();

   construirBarraInferior();
  

  }

  // =========================================================
  // BARRA SUPERIOR
  // =========================================================

  private void construirBarraSuperior() {

  
   JToolBar barra = new JToolBar();

   barra.setFloatable(false);

   JLabel nivelLabel =
           new JLabel(" Nivel: ");

   comboNivel =
           new JComboBox<>();

   comboNivel.addItem("Nivel 1");
   comboNivel.addItem("Nivel 2");
   comboNivel.addItem("Nivel 3");
   comboNivel.addItem("Nivel 4");
   comboNivel.addItem("Nuevo nivel");

   comboNivel.addActionListener(
           new ActionListener() {

               @Override
               public void actionPerformed(ActionEvent e) {

                   int indice =
                           comboNivel.getSelectedIndex();

                   if (indice >= 0 && indice <= 3) {

                       cargarNivelExistente(indice + 1);

                   } else if (indice == 4) {

                       crearNuevoNivel();
                   }
               }
           });

   barra.add(nivelLabel);
   barra.add(comboNivel);

   barra.addSeparator(
           new Dimension(15, 0));

   barra.add(
           new JLabel(" Tileset: "));

   comboTileset =
           new JComboBox<>();

   comboTileset.addItem("Normal");
   comboTileset.addItem("Invierno");

   comboTileset.addActionListener(
           e -> cambiarTileset());

   barra.add(comboTileset);

   barra.addSeparator(
           new Dimension(15, 0));

   JButton botonExportar =
           new JButton("Exportar Java");

   botonExportar.addActionListener(
           e -> exportarJava());

   barra.add(botonExportar);

   JButton botonMatriz =
           new JButton("Exportar matriz");

   botonMatriz.addActionListener(
           e -> exportarMatriz());

   barra.add(botonMatriz);

   JButton botonCopiar =
           new JButton("Ver código");

   botonCopiar.addActionListener(
           e -> mostrarCodigo());

   barra.add(botonCopiar);

   barra.addSeparator(
           new Dimension(15, 0));

   barra.add(
           new JLabel(" Zoom: "));

   spinnerZoom =
           new JSpinner(
                   new SpinnerNumberModel(
                           2,
                           ZOOM_MIN,
                           ZOOM_MAX,
                           1));

   spinnerZoom.addChangeListener(
           e -> {

               int zoom =
                       (Integer) spinnerZoom.getValue();

               mapaPanel.setZoom(zoom);
               tilesetPanel.setZoom(zoom);
           });

   barra.add(spinnerZoom);

   add(barra, BorderLayout.NORTH);
  

  }

  // =========================================================
  // CENTRO
  // =========================================================

  private void construirCentro() {

  
   JPanel centro =
           new JPanel(new BorderLayout());

   // -----------------------------------------------------
   // TILESET
   // -----------------------------------------------------

   JPanel panelTileset =
           new JPanel(new BorderLayout());

   panelTileset.setBorder(
           BorderFactory.createTitledBorder(
                   "Tileset"));

   tilesetPanel =
           new TilesetPanel();

   JScrollPane scrollTileset =
           new JScrollPane(tilesetPanel);

   scrollTileset.setPreferredSize(
           new Dimension(300, 600));

   panelTileset.add(
           scrollTileset,
           BorderLayout.CENTER);

   // -----------------------------------------------------
   // MAPA
   // -----------------------------------------------------

   JPanel panelMapa =
           new JPanel(new BorderLayout());

   panelMapa.setBorder(
           BorderFactory.createTitledBorder(
                   "Mapa"));

   mapaPanel =
           new MapaPanel();

   JScrollPane scrollMapa =
           new JScrollPane(mapaPanel);

   panelMapa.add(
           scrollMapa,
           BorderLayout.CENTER);

   centro.add(
           panelTileset,
           BorderLayout.WEST);

   centro.add(
           panelMapa,
           BorderLayout.CENTER);

   add(centro, BorderLayout.CENTER);
  

  }

  // =========================================================
  // BARRA INFERIOR
  // =========================================================

  private void construirBarraInferior() {

  
   JPanel inferior =
           new JPanel(new BorderLayout());

   JPanel herramientas =
           new JPanel(new GridLayout(1, 0, 5, 0));

   radioPintar =
           new JRadioButton(
                   "Pintar",
                   true);

   radioBorrar =
           new JRadioButton(
                   "Borrar");

   ButtonGroup grupo =
           new ButtonGroup();

   grupo.add(radioPintar);
   grupo.add(radioBorrar);

   herramientas.add(radioPintar);
   herramientas.add(radioBorrar);

   JButton botonBorrarTodo =
           new JButton("Borrar todo");

   botonBorrarTodo.addActionListener(
           e -> borrarTodo());

   herramientas.add(botonBorrarTodo);

   JButton botonNuevaMatriz =
           new JButton("Nuevo tamaño");

   botonNuevaMatriz.addActionListener(
           e -> crearNuevoNivel());

   herramientas.add(botonNuevaMatriz);

   inferior.add(
           herramientas,
           BorderLayout.WEST);

   JPanel informacion =
           new JPanel();

   labelTile =
           new JLabel(
                   "Tile seleccionado: 1");

   labelDimension =
           new JLabel(
                   "  Dimensión: 0 x 0");

   labelEstado =
           new JLabel(
                   "  Listo");

   informacion.add(labelTile);
   informacion.add(labelDimension);
   informacion.add(labelEstado);

   inferior.add(
           informacion,
           BorderLayout.CENTER);

   add(
           inferior,
           BorderLayout.SOUTH);
  

  }

  // =========================================================
  // CARGAR NIVEL EXISTENTE
  // =========================================================

  private void cargarNivelExistente(
  int numero) {

  
   try {

       GameDesign.TiledLayer nivel;

       if (numero == 1) {

           nivel =
                   gameDesign.getNivel_1();

       } else if (numero == 2) {

           nivel =
                   gameDesign.getNivel_2();

       } else if (numero == 3) {

           nivel =
                   gameDesign.getNivel_3();

       } else {

           nivel =
                   gameDesign.getNivel_4();
       }

       mapa =
               copiarMatriz(
                       nivel.getCells());

       numeroNivelActual =
               numero;

       nombreNivelActual =
               "Nivel " + numero;

       nivelExistente = true;

       actualizarTilesetDesdeNivel(
               numero);

       actualizarInterfazMapa();

       labelEstado.setText(
               "  Nivel " +
               numero +
               " cargado");

   } catch (Exception e) {

       mostrarError(
               "No se pudo cargar el nivel.",
               e);
   }
  

  }

  // =========================================================
  // SELECCIONAR TILESET
  // =========================================================

  private void cambiarTileset() {

  
   try {

       int indice =
               comboTileset.getSelectedIndex();

       if (indice == 0) {

           tileset =
                   gameDesign
                           .getTileset_normal();

           nombreTileset =
                   "normal";

       } else {

           tileset =
                   gameDesign
                           .getTileset_invierno();

           nombreTileset =
                   "invierno";
       }

       tilesetPanel.repaint();

       labelEstado.setText(
               "  Tileset: " +
               nombreTileset);

   } catch (Exception e) {

       mostrarError(
               "No se pudo cargar el tileset.",
               e);
   }
  

  }

  // =========================================================
  // ACTUALIZAR TILESET SEGUN NIVEL
  // =========================================================

  private void actualizarTilesetDesdeNivel(
  int numero) {

  
   try {

       if (numero == 1 ||
           numero == 2 ||
           numero == 3) {

           if (numero == 1) {

               tileset =
                       gameDesign
                               .getTileset_normal();

               comboTileset
                       .setSelectedIndex(0);

               nombreTileset =
                       "normal";

           } else {

               tileset =
                       gameDesign
                               .getTileset_invierno();

               comboTileset
                       .setSelectedIndex(1);

               nombreTileset =
                       "invierno";
           }

       } else {

           tileset =
                   gameDesign
                           .getTileset_normal();

           comboTileset
                   .setSelectedIndex(0);

           nombreTileset =
                   "normal";
       }

       tilesetPanel.repaint();

   } catch (Exception e) {

       mostrarError(
               "No se pudo cargar el tileset.",
               e);
   }
  

  }

  // =========================================================
  // NUEVO NIVEL
  // =========================================================

  private void crearNuevoNivel() {

  
   JSpinner ancho =
           new JSpinner(
                   new SpinnerNumberModel(
                           20,
                           1,
                           200,
                           1));

   JSpinner alto =
           new JSpinner(
                   new SpinnerNumberModel(
                           20,
                           1,
                           200,
                           1));

   JPanel panel =
           new JPanel(
                   new GridLayout(2, 2, 5, 5));

   panel.add(
           new JLabel("Ancho:"));

   panel.add(ancho);

   panel.add(
           new JLabel("Alto:"));

   panel.add(alto);

   int resultado =
           JOptionPane.showConfirmDialog(
                   this,
                   panel,
                   "Nuevo nivel",
                   JOptionPane.OK_CANCEL_OPTION);

   if (resultado !=
           JOptionPane.OK_OPTION) {

       return;
   }

   int columnas =
           (Integer) ancho.getValue();

   int filas =
           (Integer) alto.getValue();

   mapa =
           new int[filas][columnas];

   numeroNivelActual = 5;

   nombreNivelActual =
           "Nivel nuevo";

   nivelExistente = false;

   try {

       tileset =
               gameDesign
                       .getTileset_normal();

       comboTileset
               .setSelectedIndex(0);

       nombreTileset =
               "normal";

   } catch (Exception e) {

       mostrarError(
               "No se pudo cargar el tileset.",
               e);
   }

   actualizarInterfazMapa();

   labelEstado.setText(
           "  Nuevo mapa creado");
  

  }

  // =========================================================
  // ACTUALIZAR MAPA
  // =========================================================

  private void actualizarInterfazMapa() {

  
   if (mapa == null) {

       return;
   }

   labelDimension.setText(
           "  Dimensión: " +
           mapa[0].length +
           " x " +
           mapa.length);

   mapaPanel.revalidate();
   mapaPanel.repaint();

   tilesetPanel.repaint();
  

  }

  // =========================================================
  // BORRAR TODO
  // =========================================================

  private void borrarTodo() {

  
   if (mapa == null) {

       return;
   }

   int respuesta =
           JOptionPane.showConfirmDialog(
                   this,
                   "¿Borrar todo el nivel?",
                   "Confirmar",
                   JOptionPane.YES_NO_OPTION);

   if (respuesta !=
           JOptionPane.YES_OPTION) {

       return;
   }

   for (int y = 0;
        y < mapa.length;
        y++) {

       for (int x = 0;
            x < mapa[y].length;
            x++) {

           mapa[y][x] =
                   TILE_VACIO;
       }
   }

   mapaPanel.repaint();

   labelEstado.setText(
           "  Mapa borrado");
  

  }

  // =========================================================
  // COPIAR MATRIZ
  // =========================================================

  private int[][] copiarMatriz(
  int[][] original) {

  
   int[][] copia =
           new int[original.length][];

   for (int y = 0;
        y < original.length;
        y++) {

       copia[y] =
               original[y].clone();
   }

   return copia;
  

  }

  // =========================================================
  // EXPORTAR MATRIZ
  // =========================================================

  private void exportarMatriz() {

  
   if (mapa == null) {

       return;
   }

   JFileChooser chooser =
           new JFileChooser();

   chooser.setDialogTitle(
           "Guardar matriz");

   chooser.setSelectedFile(
           new File(
                   nombreArchivoBase()
                   + "_matriz.txt"));

   int resultado =
           chooser.showSaveDialog(this);

   if (resultado !=
           JFileChooser.APPROVE_OPTION) {

       return;
   }

   File archivo =
           chooser.getSelectedFile();

   try {

       PrintWriter out =
               new PrintWriter(
                       new FileWriter(archivo));

       out.println(
               generarMatrizJava());

       out.close();

       labelEstado.setText(
               "  Matriz exportada");

       JOptionPane.showMessageDialog(
               this,
               "Matriz exportada correctamente.");

   } catch (IOException e) {

       mostrarError(
               "No se pudo guardar la matriz.",
               e);
   }
  

  }

  // =========================================================
  // EXPORTAR METODO JAVA
  // =========================================================

  private void exportarJava() {

  
   if (mapa == null) {

       return;
   }

   JFileChooser chooser =
           new JFileChooser();

   chooser.setDialogTitle(
           "Exportar nivel Java");

   chooser.setSelectedFile(
           new File(
                   nombreArchivoBase()
                   + ".json"));

   int resultado =
           chooser.showSaveDialog(this);

   if (resultado !=
           JFileChooser.APPROVE_OPTION) {

       return;
   }

   File archivo =
           chooser.getSelectedFile();

   try {

       PrintWriter out =
               new PrintWriter(
                       new FileWriter(archivo));

       out.println(
               generarMetodoNivel());

       out.close();

       labelEstado.setText(
               "  Nivel exportado");

       JOptionPane.showMessageDialog(
               this,
               "Nivel exportado correctamente.\n\n"
               + "El archivo NO modifica "
               + "GameDesign.java.\n"
               + "Copia el método generado "
               + "manualmente.");

   } catch (IOException e) {

       mostrarError(
               "No se pudo exportar.",
               e);
   }
  

  }

  // =========================================================
  // MOSTRAR CODIGO
  // =========================================================

  private void mostrarCodigo() {

  
   if (mapa == null) {

       return;
   }

   JTextArea area =
           new JTextArea(
                   generarMetodoNivel());

   area.setFont(
           new Font(
                   Font.MONOSPACED,
                   Font.PLAIN,
                   12));

   area.setCaretPosition(0);

   JScrollPane scroll =
           new JScrollPane(area);

   scroll.setPreferredSize(
           new Dimension(850, 600));

   JOptionPane.showMessageDialog(
           this,
           scroll,
           "Código Java generado",
           JOptionPane.INFORMATION_MESSAGE);
  

  }

  // =========================================================
  // GENERAR MATRIZ JAVA
  // =========================================================

  private String generarMatrizJava() {

  
   StringBuilder sb =
           new StringBuilder();

   sb.append(
           "int[][] tiles = {\n");

   for (int y = 0;
        y < mapa.length;
        y++) {

       sb.append("    {");

       for (int x = 0;
            x < mapa[y].length;
            x++) {

           sb.append(
                   mapa[y][x]);

           if (x <
                   mapa[y].length - 1) {

               sb.append(",");
           }
       }

       sb.append("}");

       if (y <
               mapa.length - 1) {

           sb.append(",");
       }

       sb.append("\n");
   }

   sb.append(
           "};");

   return sb.toString();
  

  }

  // =========================================================
  // GENERAR METODO COMPLETO
  // =========================================================
  
  
  
  
    private String generarMetodoNivel() {

        StringBuilder sb = new StringBuilder();

        sb.append("{\n");

        sb.append("    \"tileset\": \"");
        sb.append(nombreTileset);
        sb.append("\",\n");

        sb.append("    \"mapa\": [\n");

        for (int y = 0; y < mapa.length; y++) {

            sb.append("        [");

            for (int x = 0; x < mapa[y].length; x++) {

                sb.append(mapa[y][x]);

                if (x < mapa[y].length - 1) {
                    sb.append(",");
                }
            }

            sb.append("]");

            if (y < mapa.length - 1) {
                sb.append(",");
            }

            sb.append("\n");
        }

        sb.append("    ]\n");

        sb.append("}");

        return sb.toString();
    }

  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  

  private String generarMetodoNivel1() {

  
   StringBuilder sb =
           new StringBuilder();

   String nombreMetodo;

   if (numeroNivelActual >= 1 &&
       numeroNivelActual <= 9) {

       nombreMetodo =
               "getNivel_" +
               numeroNivelActual;

   } else {

       nombreMetodo =
               "getNivel_5";
   }

   String getterTileset;

   if ("invierno".equals(
           nombreTileset)) {

       getterTileset =
               "getTileset_invierno()";

   } else {

       getterTileset =
               "getTileset_normal()";
   }

   sb.append(
           "public TiledLayer "
           + nombreMetodo
           + "() throws IOException {\n");

   sb.append(
           "    if (nivel_"
           + numeroNivelActual
           + " == null) {\n");

   sb.append("\n");

   sb.append(
           "        int[][] tiles = {\n");

   for (int y = 0;
        y < mapa.length;
        y++) {

       sb.append("            {");

       for (int x = 0;
            x < mapa[y].length;
            x++) {

           sb.append(
                   mapa[y][x]);

           if (x <
                   mapa[y].length - 1) {

               sb.append(",");
           }
       }

       sb.append("}");

       if (y <
               mapa.length - 1) {

           sb.append(",");
       }

       sb.append("\n");
   }

   sb.append(
           "        };\n\n");

   sb.append(
           "        nivel_"
           + numeroNivelActual
           + " = new TiledLayer("
           + "tiles,"
           + getterTileset
           + ",16,16);\n");

   sb.append(
           "    }\n");

   sb.append(
           "    return nivel_"
           + numeroNivelActual
           + ";\n");

   sb.append("}");

   return sb.toString();

  }

  // =========================================================
  // NOMBRE DE ARCHIVO
  // =========================================================

  private String nombreArchivoBase() {

   if (numeroNivelActual >= 1 &&
       numeroNivelActual <= 9) {

       return "nivel_"
               + numeroNivelActual;
   }

   return "nivel_nuevo";

  }

  // =========================================================
  // OBTENER TILE DEL TILESET
  // =========================================================

  private void seleccionarTile(
  int columna,
  int fila) {

   if (tileset == null) {

       return;
   }

   int columnas =
           tileset.getWidth()
           / TILE_SIZE;

   int filas =
           tileset.getHeight()
           / TILE_SIZE;

   if (columna < 0 ||
       fila < 0 ||
       columna >= columnas ||
       fila >= filas) {

       return;
   }

   
   int indice =
        fila * columnas
        + columna
        + 1;

   tileSeleccionado =
           indice;

   labelTile.setText(
           "Tile seleccionado: "
           + tileSeleccionado);

   tilesetPanel.repaint();

   labelEstado.setText(
           "  Tile "
           + tileSeleccionado
           + " seleccionado");

  }

  // =========================================================
  // PINTAR TILE
  // =========================================================

  private void pintar(
  int columna,
  int fila,
  boolean borrar) {

   if (mapa == null) {

       return;
   }

   if (fila < 0 ||
       fila >= mapa.length ||
       columna < 0 ||
       columna >= mapa[0].length) {

       return;
   }

   if (borrar ||
       radioBorrar.isSelected()) {

       mapa[fila][columna] =
               TILE_VACIO;

   } else {

       mapa[fila][columna] =
               tileSeleccionado;
   }

   mapaPanel.repaint();

   labelEstado.setText(
           "  Tile "
           + mapa[fila][columna]
           + " en ["
           + columna
           + ","
           + fila
           + "]");

  }

  // =========================================================
  // ERROR
  // =========================================================

  private void mostrarError(
  String mensaje,
  Exception e) {

   e.printStackTrace();

   JOptionPane.showMessageDialog(
           this,
           mensaje
           + "\n\n"
           + e.getMessage(),
           "Error",
           JOptionPane.ERROR_MESSAGE);

  }

  // =========================================================
  // PANEL DEL TILESET
  // =========================================================

  private class TilesetPanel
  extends JPanel {

   private int zoom = 2;

   public TilesetPanel() {

       setBackground(
               Color.DARK_GRAY);

       MouseAdapter mouse =
               new MouseAdapter() {

                   @Override
                   public void mousePressed(
                           MouseEvent e) {

                       if (tileset == null) {
                           return;
                       }

                       int x =
                               e.getX()
                               / (TILE_SIZE
                               * zoom);

                       int y =
                               e.getY()
                               / (TILE_SIZE
                               * zoom);

                       seleccionarTile(
                               x,
                               y);
                   }
               };

       addMouseListener(mouse);
   }

   public void setZoom(int zoom) {

       this.zoom = zoom;

       revalidate();
       repaint();
   }

   @Override
   public Dimension getPreferredSize() {

       if (tileset == null) {

           return new Dimension(
                   250,
                   250);
       }

       return new Dimension(
               tileset.getWidth()
               * zoom,
               tileset.getHeight()
               * zoom);
   }

   @Override
   protected void paintComponent(
           Graphics g) {

       super.paintComponent(g);

       if (tileset == null) {

           return;
       }

       Graphics2D g2 =
               (Graphics2D)
               g.create();

       g2.setRenderingHint(
               RenderingHints.KEY_INTERPOLATION,
               RenderingHints
                       .VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

       g2.drawImage(
               tileset,
               0,
               0,
               tileset.getWidth()
                       * zoom,
               tileset.getHeight()
                       * zoom,
               null);

       // -------------------------------------------------
       // CUADRICULA
       // -------------------------------------------------

       g2.setColor(
               new Color(
                       255,
                       255,
                       255,
                       60));

       g2.setStroke(
               new BasicStroke(1));

       int ancho =
               tileset.getWidth()
               * zoom;

       int alto =
               tileset.getHeight()
               * zoom;

       for (int x = 0;
            x <= ancho;
            x += TILE_SIZE * zoom) {

           g2.drawLine(
                   x,
                   0,
                   x,
                   alto);
       }

       for (int y = 0;
            y <= alto;
            y += TILE_SIZE * zoom) {

           g2.drawLine(
                   0,
                   y,
                   ancho,
                   y);
       }

       // -------------------------------------------------
       // TILE SELECCIONADO
       // -------------------------------------------------

       int columnas =
               tileset.getWidth()
               / TILE_SIZE;

       int indice =
               tileSeleccionado - 1;

       if (indice >= 0) {

           int columna =
                   indice % columnas;

           int fila =
                   indice / columnas;

           int x =
                   columna
                   * TILE_SIZE
                   * zoom;

           int y =
                   fila
                   * TILE_SIZE
                   * zoom;

           g2.setColor(
                   Color.YELLOW);

           g2.setStroke(
                   new BasicStroke(3));

           g2.drawRect(
                   x,
                   y,
                   TILE_SIZE * zoom,
                   TILE_SIZE * zoom);
       }

       g2.dispose();
   }

  }

  // =========================================================
  // PANEL DEL MAPA
  // =========================================================

  private class MapaPanel
  extends JPanel {

   private int zoom = 2;

   private boolean mostrarGrid =
           true;

   public MapaPanel() {

       setBackground(
               Color.BLACK);

       MouseAdapter mouse =
               new MouseAdapter() {

                   @Override
                   public void mousePressed(
                           MouseEvent e) {

                       int columna =
                               e.getX()
                               / (TILE_SIZE
                               * zoom);

                       int fila =
                               e.getY()
                               / (TILE_SIZE
                               * zoom);

                       boolean borrar =
                               SwingUtilities
                                       .isRightMouseButton(e);

                       pintar(
                               columna,
                               fila,
                               borrar);
                   }
               };

       addMouseListener(mouse);

       addMouseMotionListener(
               new MouseMotionAdapter() {

                   @Override
                   public void mouseDragged(
                           MouseEvent e) {

                       int columna =
                               e.getX()
                               / (TILE_SIZE
                               * zoom);

                       int fila =
                               e.getY()
                               / (TILE_SIZE
                               * zoom);

                       boolean borrar =
                               (e.getModifiersEx()
                               & InputEvent.BUTTON3_DOWN_MASK)
                               != 0;

                       pintar(
                               columna,
                               fila,
                               borrar);
                   }
               });
   }

   public void setZoom(int zoom) {

       this.zoom = zoom;

       revalidate();
       repaint();
   }

   @Override
   public Dimension getPreferredSize() {

       if (mapa == null ||
           mapa.length == 0) {

           return new Dimension(
                   500,
                   500);
       }

       return new Dimension(
               mapa[0].length
                       * TILE_SIZE
                       * zoom,

               mapa.length
                       * TILE_SIZE
                       * zoom);
   }

   @Override
   protected void paintComponent(
           Graphics g) {

       super.paintComponent(g);

       if (mapa == null ||
           tileset == null) {

           return;
       }

       Graphics2D g2 =
               (Graphics2D)
               g.create();

       g2.setRenderingHint(
               RenderingHints.KEY_INTERPOLATION,
               RenderingHints
                       .VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

       int columnasTileset =
               tileset.getWidth()
               / TILE_SIZE;

       for (int fila = 0;
            fila < mapa.length;
            fila++) {

           for (int columna = 0;
                columna < mapa[fila].length;
                columna++) {

               int tile =
                       mapa[fila][columna];

               if (tile <= 0) {

                   continue;
               }

               int indice =
                       tile - 1;

               int sx =
                       (indice
                       % columnasTileset)
                       * TILE_SIZE;

               int sy =
                       (indice
                       / columnasTileset)
                       * TILE_SIZE;

               int dx =
                       columna
                       * TILE_SIZE
                       * zoom;

               int dy =
                       fila
                       * TILE_SIZE
                       * zoom;

               g2.drawImage(
                       tileset,
                       dx,
                       dy,
                       dx
                               + TILE_SIZE
                               * zoom,
                       dy
                               + TILE_SIZE
                               * zoom,
                       sx,
                       sy,
                       sx + TILE_SIZE,
                       sy + TILE_SIZE,
                       null);
           }
       }

       // -------------------------------------------------
       // CUADRICULA
       // -------------------------------------------------

       if (mostrarGrid) {

           g2.setColor(
                   new Color(
                           255,
                           255,
                           255,
                           45));

           g2.setStroke(
                   new BasicStroke(1));

           int ancho =
                   mapa[0].length
                   * TILE_SIZE
                   * zoom;

           int alto =
                   mapa.length
                   * TILE_SIZE
                   * zoom;

           for (int x = 0;
                x <= ancho;
                x += TILE_SIZE * zoom) {

               g2.drawLine(
                       x,
                       0,
                       x,
                       alto);
           }

           for (int y = 0;
                y <= alto;
                y += TILE_SIZE * zoom) {

               g2.drawLine(
                       0,
                       y,
                       ancho,
                       y);
           }
       }

       g2.dispose();
   }

  }

  // =========================================================
  // MAIN
  // =========================================================

  public static void main(
  String[] args) {

   SwingUtilities.invokeLater(
           new Runnable() {

               @Override
               public void run() {

                   new CrearNivelesConejitoPC()
                           .setVisible(true);
               }
           });

  }
  }
