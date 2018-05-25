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
    static Corredor[] corredors = new Corredor[100];

    public static Corredor inscriureCorredor(String nom, Equip equip) throws IOException {
        if(equip == null){
            return null;
        }


        FileWriter out = new FileWriter("corredores.txt", true);

        out.write(nom + ":");
        out.write(String.valueOf(equip.id+":"));
        out.write(String.valueOf(1000+3)+"\n");

        out.close();

        return null;
    }

    public static Corredor obtenirCorredor(int id){
        try {
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( id == Integer.parseInt(partes[2])) {
                    Corredor corredor = new Corredor(partes[0], Integer.parseInt(partes[1]));
                    corredor.id = id;
                    return corredor;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Corredor[] obtenirLlistaCorredors(){
        Corredor[] corredors = new Corredor[obtenirNumeroCorredors()];
        int contador =0;

        try {
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                Corredor corredor = new Corredor(partes[0], Integer.parseInt(partes[1]));
                corredor.id = Integer.parseInt(partes[2]);
                corredors[contador] = corredor;
                contador++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return corredors;
    }

  public static Corredor[] buscarCorredorsPerNom(String nom){
      Corredor[] corredors = new Corredor[obtenirNumeroCorredors()];
        int contador = 0;
        try {
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( nom.equals(partes[0])) {
                    Corredor corredor = new Corredor(partes[0], Integer.parseInt(partes[1]));
                    corredor.id = Integer.parseInt(partes[2]);
                    corredors[contador] = corredor;
                    contador++;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return corredors;
    }

    public static boolean existeixCorredor(String nom){
        try {
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( nom.equals(partes[0])) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void modificarNomCorredor(int id, String nouNom) throws IOException {

            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            FileWriter out = new FileWriter("corredores2.txt", true);
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
        String lineacorredor;
        FileWriter out = new FileWriter("corredores2.txt", true);
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

    public static int obtenirUltimIdCorredor(){

        int id = 0;
        try {
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( Integer.parseInt(partes[2]) > id) {
                    id = Integer.parseInt(partes[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return id;

    }

    private static int obtenirNumeroCorredors(){

        int contador =0;

        try {
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){

                contador++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return contador;
    }

    public static int obtenirNumeroCorredorsPerNom(String nom){
        int num = 0;
        try {
            BufferedReader fileReader =  new BufferedReader(new FileReader("corredores.txt"));
            String lineacorredor;
            while ((lineacorredor = fileReader.readLine()) != null){
                String[] partes = lineacorredor.split(":");

                if( nom.equals(partes[0])) {
                   num = Integer.parseInt(partes[2]);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return num;
    }
}
