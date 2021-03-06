package com.company.manager;

import com.company.model.Corredor;
import com.company.model.Equip;

import java.io.*;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLOutput;

public class ManagerCorredors {

    public static Corredor inscriureCorredor(String nom, Equip equip) throws IOException {
        if(equip == null){
            return null;
        }

        FileWriter out = new FileWriter("corredores.txt", true);

        out.write(nom + ":");
        out.write(equip.id+":");
        out.write((obtenirUltimIdCorredor()+1) + "\n");
        out.close();

        Corredor corredor = new Corredor(nom, equip.id);
        corredor.id = obtenirUltimIdCorredor()+1;
        return corredor;
    }

    public static Corredor obtenirCorredor(int id) throws IOException {

            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( id == Integer.parseInt(partes[2])) {
                    Corredor corredor = new Corredor(partes[0], Integer.parseInt(partes[1]));
                    corredor.id = id;
                    fileReader.close();
                    return corredor;
                }
            }

        fileReader.close();
        return null;
    }

    public static Corredor[] obtenirLlistaCorredors() throws IOException {
        Corredor[] corredors = new Corredor[obtenirNumeroCorredors()];
        int contador =0;

            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                Corredor corredor = new Corredor(partes[0], Integer.parseInt(partes[1]));
                corredor.id = Integer.parseInt(partes[2]);
                corredors[contador] = corredor;
                contador++;

            }
        fileReader.close();
        return corredors;
    }

  public static Corredor[] buscarCorredorsPerNom(String nom) throws IOException {
      Corredor[] corredors = new Corredor[obtenirNumeroCorredorsPerNom(nom)];
        int contador = 0;
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( partes[0].toLowerCase().contains(nom.toLowerCase())) {
                    Corredor corredor = new Corredor(partes[0], Integer.parseInt(partes[1]));
                    corredor.id = Integer.parseInt(partes[2]);
                    corredors[contador] = corredor;
                    contador++;
                }

            }
        fileReader.close();
        return corredors;
    }

    public static boolean existeixCorredor(String nom) throws IOException {
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( nom.equals(partes[0])) {
                    return true;
                }
            }

        fileReader.close();
        return false;
    }

    public static void modificarNomCorredor(int id, String nouNom) throws IOException {

            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            FileWriter out = new FileWriter("corredores2.txt", true);
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null) {
                String[] partes = lineacorredor.split(":");


                if (id != Integer.parseInt(partes[2])) {

                    out.write(partes[0] + ":");
                    out.write(partes[1] + ":");
                    out.write(partes[2] + "\n");

                } else {
                    out.write(nouNom + ":");
                    out.write(partes[1] + ":");
                    out.write(partes[2] + "\n");
                }
            }
            out.close();
            fileReader.close();


        Files.move(FileSystems.getDefault().getPath("corredores2.txt"), FileSystems.getDefault().getPath("corredores.txt"), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void modificarEquipCorredor(int id, Equip nouEquip) throws IOException {
        if(nouEquip == null){
            return;
        }
        BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
        FileWriter out = new FileWriter("corredores2.txt", true);
        String lineacorredor;
        while ((lineacorredor = fileReader.readLine()) != null) {
            String[] partes = lineacorredor.split(":");


            if (id != Integer.parseInt(partes[2])) {

                out.write(partes[0] + ":");
                out.write(partes[1] + ":");
                out.write(partes[2] + "\n");

            } else {
                out.write(partes[0] + ":");
                out.write(nouEquip.id + ":");
                out.write(partes[2] + "\n");
            }
        }
        out.close();
        fileReader.close();

        Files.move(FileSystems.getDefault().getPath("corredores2.txt"), FileSystems.getDefault().getPath("corredores.txt"), StandardCopyOption.REPLACE_EXISTING);
    }


    public static void esborrarCorredor(int id) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader("corredores.txt"));
        String lineacorredor;
        FileWriter out = new FileWriter("corredores2.txt", true);
        while ((lineacorredor = fileReader.readLine()) != null) {
            String[] partes = lineacorredor.split(":");


            if (id != Integer.parseInt(partes[2])) {

                out.write(partes[0] + ":");
                out.write(partes[1] + ":");
                out.write(partes[2] + "\n");
            }
        }

        out.close();
        fileReader.close();


        Files.move(FileSystems.getDefault().getPath("corredores2.txt"), FileSystems.getDefault().getPath("corredores.txt"), StandardCopyOption.REPLACE_EXISTING);
    }

    public static int obtenirUltimIdCorredor() throws IOException {

        int id = 0;
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( Integer.parseInt(partes[2]) > id) {
                    id = Integer.parseInt(partes[2]);
                }
            }
        fileReader.close();
        return id;

    }

    private static int obtenirNumeroCorredors() throws IOException {

        int contador =0;

            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){

                contador++;

            }

        fileReader.close();
        return contador;
    }

    public static int obtenirNumeroCorredorsPerNom(String nom) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("corredores.txt"));
        String c;
        int count = 0;
        while ((c = reader.readLine()) != null) {
            String[] partes = c.split(":");
            if(partes[0].toLowerCase().contains(nom.toLowerCase())){
                count++;
            }

        }
        reader.close();
        return count;
    }
}

