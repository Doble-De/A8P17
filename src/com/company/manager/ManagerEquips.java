package com.company.manager;

import com.company.model.Equip;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.WRITE;

public class ManagerEquips {
    static Equip[] equips = new Equip[100];
    static int MAXNOM = 12;
    static int MAXID = 4;

    public static Equip inscriureEquip(String nom) throws IOException {
        byte data[] = nom.getBytes();
        ByteBuffer out = ByteBuffer.wrap(data);

        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long length = fc.size();
        fc.position(length);

        fc.write(ByteBuffer.wrap(nom.getBytes()));

        int id = ((int) length / (MAXNOM + MAXID)) + 1;

        ByteBuffer byteBuffer = ByteBuffer.allocate(MAXID);
        byteBuffer.putInt(0, id);
        fc.position(length + MAXNOM);
        fc.write(byteBuffer);
        return null;
    }

    public static Equip obtenirEquip(int id) throws IOException {
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long posFinal = fc.size();
        fc.position((id - 1) * (MAXNOM + MAXID));
        ByteBuffer byteBufferNom = ByteBuffer.allocate(MAXNOM);

        fc.read(byteBufferNom);
        String nom = new String(byteBufferNom.array(), Charset.forName("UTF-8"));
        Equip equip = new Equip(nom);
        equip.id = id;

        return equip;
    }

    public static Equip obtenirEquip(String nom) throws IOException {

        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long posFinal = fc.size();
        int recorrer = 0;

        fc.position(recorrer);
        while (recorrer < posFinal) {
            fc.position(recorrer);
            ByteBuffer byteBufferNom = ByteBuffer.allocate(MAXNOM);

            fc.read(byteBufferNom);
            String nombre = new String(byteBufferNom.array(), Charset.forName("UTF-8"));
            int larg = nom.length();
            String nomb = nombre.substring(0, larg);

            if (nomb.toLowerCase().equals(nom.toLowerCase())) {
                Equip equip = new Equip(nombre);
                recorrer = recorrer + MAXNOM;
                fc.position(recorrer);
                ByteBuffer byteBufferNum = ByteBuffer.allocate(MAXID);
                fc.read(byteBufferNum);
                int numero = byteBufferNum.getInt(0);
                equip.id = numero;
                return equip;
            }
            recorrer += MAXNOM + MAXID;
        }
        return null;
    }

    public static String obtenirNomEquip(int id) throws IOException {
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long posFinal = fc.size();
        fc.position((id - 1) * (MAXNOM + MAXID));
        ByteBuffer byteBufferNom = ByteBuffer.allocate(MAXNOM);

        fc.read(byteBufferNom);
        String nom = new String(byteBufferNom.array(), Charset.forName("UTF-8"));
        Equip equip = new Equip(nom);
        equip.id = id;

        return equip.nom;
    }

    public static Equip[] obtenirLlistaEquips() throws IOException {
        Equip[] llistaEquips = new Equip[obtenirNumeroEquips()];
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long posFinal = fc.size();
        int recorrer = 0;
        int cont = 0;


        while (recorrer < posFinal) {
            fc.position(recorrer);
            ByteBuffer byteBufferNom = ByteBuffer.allocate(MAXNOM);

            fc.read(byteBufferNom);
            int larg = byteBufferNom.getInt(0);

            if (larg != 0) {
                String nombre = new String(byteBufferNom.array(), Charset.forName("UTF-8"));
                larg = nombre.length();
                String nom = nombre.substring(0, larg);
                recorrer = recorrer + MAXNOM;
                fc.position(recorrer);
                ByteBuffer byteBufferNum = ByteBuffer.allocate(MAXID);
                fc.read(byteBufferNum);
                int numero = byteBufferNum.getInt(0);
                Equip equip = new Equip(nom);
                equip.id = numero;
                llistaEquips[cont] = equip;
                cont++;
                recorrer -= MAXNOM;
            }

            recorrer += MAXNOM + MAXID;
        }

        return llistaEquips;
    }

    public static Equip[] buscarEquipsPerNom(String nom) throws IOException {
        Equip[] llistaEquips = new Equip[obtenirNumeroEquipsPerNom(nom)];
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long posFinal = fc.size();
        int recorrer = 0;
        int cont = 0;

        fc.position(recorrer);

        while (recorrer < posFinal) {
            fc.position(recorrer);
            ByteBuffer byteBufferNom = ByteBuffer.allocate(MAXNOM);
            fc.read(byteBufferNom);
            String nombre = new String(byteBufferNom.array(), Charset.forName("UTF-8"));
            int larg = nom.length();
            String nomb = nombre.substring(0, larg);

            if (nomb.toLowerCase().contains(nom.toLowerCase())) {
                Equip equip = new Equip(nombre);
                recorrer = recorrer + MAXNOM;
                fc.position(recorrer);
                ByteBuffer byteBufferNum = ByteBuffer.allocate(MAXID);
                fc.read(byteBufferNum);
                int numero = byteBufferNum.getInt(0);
                equip.id = numero;
                llistaEquips[cont] = equip;
                cont++;
                recorrer -= MAXNOM;
            }

            recorrer += MAXNOM + MAXID;
        }

        return llistaEquips;
    }

    public static boolean existeixEquip(String nom) throws IOException {
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long posFinal = fc.size();
        int recorrer = 0;

        fc.position(recorrer);
        while (recorrer < posFinal) {
            fc.position(recorrer);
            ByteBuffer byteBufferNom = ByteBuffer.allocate(MAXNOM);

            fc.read(byteBufferNom);
            String nombre = new String(byteBufferNom.array(), Charset.forName("UTF-8"));
            int larg = nom.length();
            String nomb = nombre.substring(0, larg);

            if (nomb.toLowerCase().equals(nom.toLowerCase())) {
                return true;
            }
            recorrer += MAXNOM + MAXID;
        }
        return false;
    }

    public static void modificarNomEquip(int id, String nouNom) throws IOException {
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        int recorrer = 0;
        long posFinal = fc.size();

        String nombre = nouNom;
        if (nouNom.length() > MAXNOM){
            nombre = nouNom.substring(0, MAXNOM);
        }

        while (recorrer < posFinal) {
            fc.position(recorrer);
            recorrer += MAXNOM;
            fc.position(recorrer);
            ByteBuffer byteBufferNum = ByteBuffer.allocate(MAXID);
            fc.read(byteBufferNum);
            int numero = byteBufferNum.getInt(0);
            if (numero == id) {
                fc.position(recorrer - MAXNOM);
                if (nombre.length() < MAXNOM) {
                    fc.write(ByteBuffer.wrap(nombre.getBytes()));
                    ByteBuffer byteBufferZero = ByteBuffer.allocate(MAXNOM - nombre.length());
                    fc.write(byteBufferZero);
                }
                else {
                    fc.write(ByteBuffer.wrap(nombre.getBytes()));
                }
            }
            recorrer -= MAXNOM;
            recorrer += MAXNOM+MAXID;
        }
    }

    public static void esborrarEquip(int id) throws IOException {
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        int recorrer = 0;
        long posFinal = fc.size();

        while (recorrer < posFinal){
            fc.position(recorrer);
            ByteBuffer byteBufferNom = ByteBuffer.allocate(4);

            fc.read(byteBufferNom);
            int larg = byteBufferNom.getInt(0);

            if (larg != 0) {
                recorrer += MAXNOM;
                fc.position(recorrer);
                ByteBuffer byteBufferNum = ByteBuffer.allocate(MAXID);
                fc.read(byteBufferNum);
                int numero = byteBufferNum.getInt(0);
                if (numero == id) {
                    fc.position(recorrer - MAXNOM);

                    ByteBuffer byteBufferZero = ByteBuffer.allocate(MAXNOM + MAXID);

                    fc.write(byteBufferZero);
                }
            }
            else {
                recorrer += MAXNOM;
            }

            recorrer += MAXID;
        }

    }

    private static int obtenirUltimIdEquip() throws IOException {
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        int maxId = 0;
        int recorrer = 0;
        long posFinal = fc.size();
        while (recorrer < posFinal) {

            fc.position(recorrer);
            ByteBuffer byteBufferNom = ByteBuffer.allocate(4);

            fc.read(byteBufferNom);
            int larg = byteBufferNom.getInt(0);

            if (larg != 0) {

                recorrer = recorrer + MAXNOM;
                fc.position(recorrer);
                ByteBuffer byteBufferNum = ByteBuffer.allocate(MAXID);
                fc.read(byteBufferNum);
                int numero = byteBufferNum.getInt(0);

                if (numero > maxId) {
                    maxId = numero;
                }
                recorrer -= MAXNOM;
            }
            recorrer += MAXNOM + MAXID;
        }
        return maxId;
    }

    private static int obtenirNumeroEquips() throws IOException {
        int count = 0;
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long posFinal = fc.size();
        int recorrer = 0;

        fc.position(recorrer);
        while (recorrer < posFinal) {
            fc.position(recorrer);
            ByteBuffer byteBufferNum = ByteBuffer.allocate(4);

            fc.read(byteBufferNum);
            int larg = byteBufferNum.getInt(0);


            if (larg != 0) {
                count++;
            }
            recorrer += MAXNOM + MAXID;
        }
        return count;
    }

    private static int obtenirNumeroEquipsPerNom(String nom) throws IOException {
        int count = 0;
        FileChannel fc = (FileChannel.open(FileSystems.getDefault().getPath("equips.txt"), READ, WRITE, CREATE));
        long posFinal = fc.size();
        int recorrer = 0;

        while (recorrer < posFinal) {
            fc.position(recorrer);
            ByteBuffer byteBufferNom = ByteBuffer.allocate(MAXNOM);
            fc.read(byteBufferNom);
            String nombre = new String(byteBufferNom.array(), Charset.forName("UTF-8"));
            int larg = nom.length();
            String nomb = nombre.substring(0, larg);
            ByteBuffer byteBufferNum = ByteBuffer.allocate(4);

            if (larg != 0) {
                if (nomb.toLowerCase().equals(nom.toLowerCase())) {
                    count++;
                }
            }
            recorrer += MAXNOM + MAXID;
        }
        return count;
    }
}
