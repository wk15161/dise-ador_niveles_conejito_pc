/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package crearnivelesconejitopc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

public class GameDesign {

    private BufferedImage platform_tiles;
    private BufferedImage tileset;
    private BufferedImage bobby_right;
    private BufferedImage bobby_left;
    private BufferedImage bobby_down;
    private BufferedImage bobby_up;
    private BufferedImage bobby_fade;
    private BufferedImage tile_finish;
    private BufferedImage bobby_death;
    private BufferedImage hud;
    private BufferedImage tileset___copia;
    private BufferedImage tileset_invierno;
    private BufferedImage tileset_normal;
    private BufferedImage tileset_acuatico;

    private Sprite bobby_derecha;
    private Sprite bobby_izquierda;
    private Sprite bobby_abajo;
    private Sprite bobby_arriba;
    private Sprite bobby_aparecer;
    private Sprite bobby_desaparec;
    private Sprite bobby_meta;
    private Sprite bobby_muerto;

    private TiledLayer nivel_1;
    private TiledLayer nivel_2;
    private TiledLayer nivel_3;
    private TiledLayer nivel_4;

    public int bobby_derechaseq001Delay = 30;
    public int[] bobby_derechaseq001 = {3,4,5,6,7,0,1,2};

    public int bobby_izquierdaseq001Delay = 30;
    public int[] bobby_izquierdaseq001 = {3,4,5,6,7,0,1,2};

    public int bobby_abajoseq001Delay = 30;
    public int[] bobby_abajoseq001 = {3,4,5,6,7,0,1,2};

    public int bobby_arribaseq001Delay = 30;
    public int[] bobby_arribaseq001 = {3,4,5,6,7,0,1,2};

    public int bobby_aparecerseq001Delay = 40;
    public int[] bobby_aparecerseq001 = {8,7,6,5,4,3,2,1,0};

    public int bobby_desaparecseq001Delay = 50;
    public int[] bobby_desaparecseq001 = {0,1,2,3,4,5,6,7,8};

    public int bobby_metaseq001Delay = 30;
    public int[] bobby_metaseq001 = {0,1,2,3};

    public int bobby_muertoseq002Delay = 40;
    public int[] bobby_muertoseq002 =
            {0,1,2,3,4,0,1,2,3,4,0,1,2,3,4,5,6,7};

    private String nombreTilesActual;
    private int tipoTilesActual;

    private BufferedImage cargar(String nombre) throws IOException {
        InputStream in = getClass().getResourceAsStream("/" + nombre);
        if (in == null) {
            throw new IOException("No se encontró el recurso: " + nombre);
        }
        return ImageIO.read(in);
    }

    public String getNombreTilesActual() {
        return nombreTilesActual;
    }

    public int getTipoTilesActual() {
        return tipoTilesActual;
    }

    public BufferedImage getPlatform_tiles() throws IOException {
        if (platform_tiles == null)
            platform_tiles = cargar("platform_tiles.png");
        return platform_tiles;
    }

    public BufferedImage getTileset() throws IOException {
        if (tileset == null)
            tileset = cargar("tileset.png");
        return tileset;
    }

    public BufferedImage getBobby_right() throws IOException {
        if (bobby_right == null)
            bobby_right = cargar("bobby_right.png");
        return bobby_right;
    }

    public Sprite getBobby_derecha() throws IOException {
        if (bobby_derecha == null) {
            bobby_derecha = new Sprite(getBobby_right(),18,25);
            bobby_derecha.setFrameSequence(bobby_derechaseq001);
            bobby_derecha.setFrameDelay(bobby_derechaseq001Delay);
        }
        return bobby_derecha;
    }

    public BufferedImage getBobby_left() throws IOException {
        if (bobby_left == null)
            bobby_left = cargar("bobby_left.png");
        return bobby_left;
    }

    public Sprite getBobby_izquierda() throws IOException {
        if (bobby_izquierda == null) {
            bobby_izquierda = new Sprite(getBobby_left(),18,25);
            bobby_izquierda.setFrameSequence(bobby_izquierdaseq001);
            bobby_izquierda.setFrameDelay(bobby_izquierdaseq001Delay);
        }
        return bobby_izquierda;
    }

    public BufferedImage getBobby_down() throws IOException {
        if (bobby_down == null)
            bobby_down = cargar("bobby_down.png");
        return bobby_down;
    }

    public Sprite getBobby_abajo() throws IOException {
        if (bobby_abajo == null) {
            bobby_abajo = new Sprite(getBobby_down(),18,25);
            bobby_abajo.setFrameSequence(bobby_abajoseq001);
            bobby_abajo.setFrameDelay(bobby_abajoseq001Delay);
        }
        return bobby_abajo;
    }

    public BufferedImage getBobby_up() throws IOException {
        if (bobby_up == null)
            bobby_up = cargar("bobby_up.png");
        return bobby_up;
    }

    public Sprite getBobby_arriba() throws IOException {
        if (bobby_arriba == null) {
            bobby_arriba = new Sprite(getBobby_up(),18,25);
            bobby_arriba.setFrameSequence(bobby_arribaseq001);
            bobby_arriba.setFrameDelay(bobby_arribaseq001Delay);
        }
        return bobby_arriba;
    }

    public BufferedImage getBobby_fade() throws IOException {
        if (bobby_fade == null)
            bobby_fade = cargar("bobby_fade.png");
        return bobby_fade;
    }

    public Sprite getBobby_aparecer() throws IOException {
        if (bobby_aparecer == null) {
            bobby_aparecer = new Sprite(getBobby_fade(),18,25);
            bobby_aparecer.setFrameSequence(bobby_aparecerseq001);
            bobby_aparecer.setFrameDelay(bobby_aparecerseq001Delay);
        }
        return bobby_aparecer;
    }

    public BufferedImage getTile_finish() throws IOException {
        if (tile_finish == null)
            tile_finish = cargar("tile_finish.png");
        return tile_finish;
    }

    public Sprite getBobby_meta() throws IOException {
        if (bobby_meta == null) {
            bobby_meta = new Sprite(getTile_finish(),16,16);
            bobby_meta.setFrameSequence(bobby_metaseq001);
            bobby_meta.setFrameDelay(bobby_metaseq001Delay);
        }
        return bobby_meta;
    }

    public Sprite getBobby_desaparec() throws IOException {
        if (bobby_desaparec == null) {
            bobby_desaparec = new Sprite(getBobby_fade(),18,25);
            bobby_desaparec.setFrameSequence(bobby_desaparecseq001);
            bobby_desaparec.setFrameDelay(bobby_desaparecseq001Delay);
        }
        return bobby_desaparec;
    }

    public BufferedImage getHud() throws IOException {
        if (hud == null)
            hud = cargar("hud.png");
        return hud;
    }

    public BufferedImage getBobby_death() throws IOException {
        if (bobby_death == null)
            bobby_death = cargar("bobby_death.png");
        return bobby_death;
    }

    public Sprite getBobby_muerto() throws IOException {
        if (bobby_muerto == null) {
            bobby_muerto = new Sprite(getBobby_death(),22,27);
            bobby_muerto.setFrameSequence(bobby_muertoseq002);
            bobby_muerto.setFrameDelay(bobby_muertoseq002Delay);
        }
        return bobby_muerto;
    }

    public BufferedImage getTileset___copia() throws IOException {
        if (tileset___copia == null)
            tileset___copia = cargar("tileset - copia.png");
        return tileset___copia;
    }

    public BufferedImage getTileset_normal() throws IOException {
        if (tileset_normal == null) {
            tipoTilesActual = 1;
            nombreTilesActual = "tileset_normal.png";
            tileset_normal = cargar("tileset_normal.png");
        }
        return tileset_normal;
    }

    public BufferedImage getTileset_invierno() throws IOException {
        if (tileset_invierno == null) {
            tipoTilesActual = 2;
            nombreTilesActual = "tileset_invierno.png";
            tileset_invierno = cargar("tileset_invierno.png");
        }
        return tileset_invierno;
    }
    
    
    
    public BufferedImage getTileset_acuatico() throws IOException {
        if (tileset_acuatico == null) {
            tipoTilesActual = 3;
            nombreTilesActual = "tileset_acuatico.png";
            tileset_acuatico = cargar("tileset_acuatico.png");
        }
        return tileset_acuatico;
    }
    

    public TiledLayer getNivel_1() throws IOException {
        if (nivel_1 == null) {

            int[][] tiles = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,19,19,19,19,19,14,19,31,31,19,19,1,1,1,1,1},
                {1,1,1,1,19,19,19,24,19,38,19,31,31,19,22,19,1,1,1,1},
                {1,1,1,1,19,19,19,32,19,10,10,19,19,19,19,19,1,1,1,1},
                {1,1,1,1,24,19,46,46,19,10,10,19,20,20,20,23,1,1,1,1},
                {1,1,1,1,19,19,46,46,19,10,10,19,20,20,20,24,1,1,1,1},
                {1,1,1,1,19,19,46,46,19,10,10,19,20,20,20,19,1,1,1,1},
                {1,1,1,1,19,19,19,32,19,10,10,28,19,19,19,19,1,1,1,1},
                {1,1,1,1,19,19,19,25,25,29,25,30,19,19,45,19,1,1,1,1},
                {1,31,31,31,19,19,19,19,19,14,28,27,19,19,19,19,1,1,1,1},
                {1,31,31,31,31,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,37,31,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            };

            nivel_1 = new TiledLayer(tiles,getTileset_normal(),16,16);
        }
        return nivel_1;
    }

    public TiledLayer getNivel_2() throws IOException {
        if (nivel_2 == null) {
            int[][] tiles = {
                {1,1,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,1,1},
                {1,2,19,19,19,19,19,19,19,19,19,14,19,19,19,19,35,19,19,19,3,1},
                {1,2,19,19,19,11,11,11,19,19,19,31,19,19,19,19,19,19,19,19,3,1},
                {1,2,19,19,19,12,12,12,19,19,19,14,19,19,19,19,19,19,19,19,3,1},
                {1,2,19,19,19,19,19,19,19,19,19,14,19,19,19,19,19,19,19,19,22,1},
                {1,2,19,19,19,20,20,20,20,19,19,14,20,20,20,20,19,19,19,19,19,1},
                {1,2,19,19,19,20,20,20,20,19,19,14,20,20,20,20,19,19,19,19,3,1},
                {1,2,19,19,19,20,20,20,20,19,19,14,20,20,20,20,19,19,19,19,3,1},
                {1,2,15,13,13,13,13,13,13,13,34,14,19,19,19,19,19,19,19,19,3,1},
                {1,2,19,19,19,19,19,19,19,1,20,14,19,19,19,19,19,19,19,19,3,1},
                {1,2,19,19,19,19,19,19,19,19,19,15,16,36,11,11,19,19,19,19,3,1},
                {1,2,19,19,19,19,19,20,19,19,19,19,20,19,12,12,14,19,19,19,3,1},
                {1,2,19,19,20,19,20,20,19,19,19,19,20,20,19,19,14,19,19,19,3,1},
                {1,2,19,19,20,20,20,19,19,19,19,19,19,20,20,20,14,19,19,19,3,1},
                {1,2,19,19,19,19,20,20,20,20,20,20,20,20,19,19,14,19,19,19,3,1},
                {1,45,19,19,19,19,19,19,19,19,19,19,19,19,19,19,14,19,19,19,3,1},
                {1,2,19,19,19,19,19,19,19,19,19,19,19,19,19,19,14,19,19,19,3,1},
                {1,2,19,19,19,19,19,19,19,19,19,19,19,19,19,19,14,19,19,19,3,1},
                {1,1,19,19,19,19,19,19,19,19,19,19,19,19,19,19,14,19,19,33,3,1},
                {1,1,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,1,1}
            };

            nivel_2 = new TiledLayer(tiles,getTileset_invierno(),16,16);
        }
        return nivel_2;
    }

    public TiledLayer getNivel_3() throws IOException {
        if (nivel_3 == null) {
            int[][] tiles = {
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,11,11,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,11,11,12,12,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,11,12,12,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,12,1,1,22,19,19,19,19,19,36,31,31,31,37,1,1,11,11,11,11,11,1},
                {1,1,1,1,19,19,19,19,19,19,13,13,13,13,34,1,1,12,12,12,12,12,1},
                {1,1,1,1,19,19,19,19,19,19,19,19,19,19,19,1,1,1,1,1,1,1,1},
                {1,1,1,1,19,8,11,9,19,19,8,11,9,19,19,1,1,1,19,33,1,1,1},
                {1,1,1,1,19,7,12,6,19,19,7,12,6,19,19,1,1,1,31,31,1,1,1},
                {1,1,1,1,19,19,19,19,19,19,19,19,19,19,19,19,1,1,31,31,1,1,1},
                {1,1,1,1,19,19,19,19,19,19,19,19,19,19,19,19,1,1,31,31,1,1,1},
                {1,11,1,1,19,19,19,19,19,19,19,19,19,19,19,19,31,31,31,31,19,11,1},
                {1,12,1,1,19,19,19,19,19,19,19,19,19,19,19,19,31,31,31,19,19,12,1},
                {1,1,1,1,19,19,19,19,19,19,19,19,19,19,19,1,1,31,31,1,1,1,1},
                {1,1,1,1,1,1,1,19,1,1,19,19,19,19,1,1,1,31,35,1,1,1,1},
                {1,1,1,1,20,20,20,20,20,1,1,1,1,38,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,20,20,20,20,20,1,1,1,1,19,1,1,1,1,1,11,11,11,1},
                {1,1,1,1,20,20,20,20,20,1,1,1,1,19,19,19,45,1,1,12,12,12,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
                {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
            };

            nivel_3 = new TiledLayer(tiles,getTileset_invierno(),16,16);
        }
        return nivel_3;
    }

    
    
    
    
    
    
    public TiledLayer getNivel_4() throws IOException {
        if (nivel_4 == null) {
            int[][] tiles = {
                {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,19,19,19,32,19,46,46,46,19,19,19,19,19,19,19,46,19,19,19,5,5},
                {5,5,5,5,19,19,19,19,19,19,20,19,19,19,19,19,19,19,46,46,46,46,46,46,46,46,46,19,19,19,5,5},
                {5,5,5,5,19,19,19,19,19,22,20,19,19,19,19,19,19,19,19,19,19,19,19,19,19,11,19,19,19,19,5,5},
                {5,5,5,5,19,19,19,19,19,20,20,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,11,19,19,19,5,5},
                {5,5,5,5,19,20,20,20,20,19,19,32,19,31,31,31,31,31,31,31,31,31,19,11,19,19,11,19,19,19,5,5},
                {5,5,5,5,20,19,19,19,19,19,19,19,19,19,12,21,21,31,31,31,19,19,19,19,19,19,19,11,19,19,5,5},
                {5,5,5,5,20,19,19,19,19,19,19,19,19,19,31,31,31,31,19,19,19,19,19,19,19,19,19,11,19,19,5,5},
                {5,5,5,5,20,20,19,19,19,19,19,19,21,21,19,19,19,19,32,19,19,19,19,19,19,19,19,11,19,19,5,5},
                {5,5,5,5,5,20,19,19,19,21,21,21,21,19,19,19,19,19,19,19,19,19,19,19,19,19,11,19,11,19,5,5},
                {5,5,5,5,5,20,20,19,19,19,19,19,19,32,19,32,19,19,19,19,32,19,19,19,19,19,11,11,11,19,5,5},
                {5,5,5,5,19,19,20,20,19,19,19,32,19,19,19,19,32,19,19,19,32,37,19,19,19,11,37,19,11,19,5,5},
                {5,5,5,5,32,19,19,19,19,19,19,19,19,19,32,19,19,19,32,19,19,19,19,19,19,11,19,19,11,19,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,32,19,19,32,19,19,19,19,19,19,19,19,19,19,11,33,19,11,19,5,5},
                {5,5,5,5,1,32,19,19,19,19,19,32,45,32,19,19,19,19,19,19,19,19,19,19,19,19,11,19,19,19,5,5},
                {5,5,5,5,19,19,19,19,19,32,19,32,32,19,32,32,32,19,19,19,19,19,19,5,19,19,11,19,11,19,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,19,19,19,32,45,32,19,32,19,19,19,19,19,19,19,11,11,11,19,5,5},
                {5,5,5,5,19,32,19,19,19,19,19,19,13,13,13,13,13,19,19,19,19,19,19,33,19,19,19,19,19,19,5,5},
                {5,5,5,5,19,19,19,32,19,19,19,19,13,19,19,19,13,13,13,13,13,13,13,13,13,34,13,13,34,34,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,19,13,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,5,5},
                {5,5,5,5,19,32,19,19,19,19,18,13,13,19,19,19,19,19,19,19,32,19,19,19,19,19,19,19,19,27,5,5},
                {5,5,5,32,19,19,19,19,19,19,19,13,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,45,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,13,19,19,19,19,19,19,19,19,19,19,19,19,19,45,19,19,19,19,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,13,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,13,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,13,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,5,5},
                {5,5,5,5,19,19,19,19,19,19,19,13,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,19,5,5},
                {5,5,5,5,12,12,12,12,12,12,3,3,19,19,19,19,19,19,19,19,19,19,19,3,3,3,5,5,3,3,3,5},
                {5,5,5,5,35,35,35,35,35,35,36,36,36,36,36,36,3,3,3,10,3,3,3,5,5,3,3,5,5,5,5,5},
                {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5}
            };

            nivel_4 = new TiledLayer(tiles,getTileset_normal(),16,16);
        }
        return nivel_4;
    }
    
    
   
    
    
    
    
    
    


    public static class Sprite {

        private BufferedImage image;
        private int frameWidth;
        private int frameHeight;
        private int[] frameSequence;
        private int frame;
        private int frameDelay = 100;
        private long lastUpdate;

        private int x;
        private int y;

        public Sprite(BufferedImage image,int frameWidth,int frameHeight) {
            this.image = image;
            this.frameWidth = frameWidth;
            this.frameHeight = frameHeight;
            this.lastUpdate = System.currentTimeMillis();
        }

        public void setFrameSequence(int[] sequence) {
            this.frameSequence = sequence;
            this.frame = 0;
        }
        
        public int getCurrentFrame() {
            return this.frame;
        }

        public void setFrameDelay(int delay) {
            this.frameDelay = delay;
        }

        public void nextFrame() {
            if (frameSequence == null || frameSequence.length == 0)
                return;

            long now = System.currentTimeMillis();

            if (now - lastUpdate >= frameDelay) {
                frame++;
                if (frame >= frameSequence.length)
                    frame = 0;
                lastUpdate = now;
            }
        }

        public int getFrame() {
            if (frameSequence == null || frameSequence.length == 0)
                return 0;
            return frameSequence[frame];
        }
        
        public int getFrameSequenceLength() {
            if (frameSequence == null)
                return 0;

            return frameSequence.length;
        }

        public void setFrame(int frame) {
            if (frameSequence == null || frameSequence.length == 0)
                return;

            if (frame < 0)
                frame = 0;

            if (frame >= frameSequence.length)
                frame = frameSequence.length - 1;

            this.frame = frame;
            this.lastUpdate = System.currentTimeMillis();
        }

        public BufferedImage getImage() {
            return image;
        }

        public int getFrameWidth() {
            return frameWidth;
        }

        public int getFrameHeight() {
            return frameHeight;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setPosition(int x,int y) {
            this.x = x;
            this.y = y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
        
        
        public void paint(Graphics2D g) {

            if (image == null || frameSequence == null
                    || frameSequence.length == 0) {
                return;
            }

            int frameIndex = frameSequence[frame];

            int columnas =
                    image.getWidth() / frameWidth;

            int sx =
                    (frameIndex % columnas) * frameWidth;

            int sy =
                    (frameIndex / columnas) * frameHeight;

            g.drawImage(
                    image,
                    x,
                    y,
                    x + frameWidth,
                    y + frameHeight,
                    sx,
                    sy,
                    sx + frameWidth,
                    sy + frameHeight,
                    null);
        }
    }


    public static class TiledLayer {

        private final int[][] cells;
        private final BufferedImage tileset;
        private final int tileWidth;
        private final int tileHeight;

        public TiledLayer(
                int[][] cells,
                BufferedImage tileset,
                int tileWidth,
                int tileHeight) {

            this.cells = cells;
            this.tileset = tileset;
            this.tileWidth = tileWidth;
            this.tileHeight = tileHeight;
        }

        public int getWidth() {
            return cells[0].length;
        }

        public int getHeight() {
            return cells.length;
        }

        public int getCell(int col,int row) {
            return cells[row][col];
        }

        public void setCell(int col,int row,int tile) {
            cells[row][col] = tile;
        }

        public int getTileWidth() {
            return tileWidth;
        }

        public int getTileHeight() {
            return tileHeight;
        }

        public BufferedImage getTileset() {
            return tileset;
        }

        public int[][] getCells() {
            return cells;
        }
        
        public void paint(Graphics2D g) {

            if (tileset == null) {
                return;
            }

            int columnas =
                    tileset.getWidth() / tileWidth;

            for (int row = 0; row < cells.length; row++) {

                for (int col = 0; col < cells[row].length; col++) {

                    int tile = cells[row][col];

                    if (tile <= 0) {
                        continue;
                    }

                    int tileIndex = tile - 1;

                    int sx =
                            (tileIndex % columnas) * tileWidth;

                    int sy =
                            (tileIndex / columnas) * tileHeight;

                    int dx =
                            col * tileWidth;

                    int dy =
                            row * tileHeight;

                    g.drawImage(
                            tileset,
                            dx,
                            dy,
                            dx + tileWidth,
                            dy + tileHeight,
                            sx,
                            sy,
                            sx + tileWidth,
                            sy + tileHeight,
                            null);
                }
            }
        }
    }
}
