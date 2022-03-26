package sort;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

public class Sort{

  int[] numeros;

  public Sort(String archivo, int framerate, String metodo){
    EventQueue.invokeLater(new Runnable(){
      @Override
      public void run(){
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFrame frame = new JFrame("Ordenamientos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new Contenedor(archivo, framerate, metodo));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      }catch(Exception e){
        System.out.println("\t:(");
      }
      }
    });
  }

  public class Contenedor extends JPanel{

    private JLabel etiqueta;

    public Contenedor(String archivo, int framerate, String metodo){
      setLayout(new BorderLayout());
      etiqueta = new JLabel(new ImageIcon(createImage(archivo)));
      add(etiqueta);
      JButton botonOrdenar = new JButton("Ordenar");
      add(botonOrdenar, BorderLayout.SOUTH);
      botonOrdenar.addActionListener(new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
          BufferedImage imagen = (BufferedImage) ((ImageIcon) etiqueta.getIcon()).getImage();
          new UpdateWorker(imagen, etiqueta, archivo, framerate, metodo).execute();
        }
      });

    }

    public BufferedImage createImage(String archivo){
      BufferedImage imagen = null;
      try{
        imagen = ImageIO.read(new File("resource/"+archivo));
        ataqueHackerman(imagen);
        Graphics2D g = imagen.createGraphics();
        g.dispose();
      }catch(Exception e){
        System.err.println("(-)\tAsegurate de estar en el directorio 'src'");
        System.err.println("\ty de haber escrito bien el nombre de imagen (la cual debe estar en la carpeta resource)");
      }
      return imagen;
    }

    public void ataqueHackerman(BufferedImage imagen){
      int length = imagen.getHeight()*imagen.getWidth();
      numeros = new int[length];
      for(int i = 0; i < numeros.length; i++)
        numeros[i] = i;
      Random r = new Random();
      for(int i = 0; i < length; i++){
        int j = r.nextInt(length);
        swapImagen(imagen, i, j);
      }
    }

    public void swapImagen(BufferedImage imagen, int i, int j){
      int colI = i%imagen.getWidth();
      int renI = i/imagen.getWidth();
      int colJ = j%imagen.getWidth();
      int renJ = j/imagen.getWidth();
      int aux = imagen.getRGB(colI, renI);
      imagen.setRGB(colI, renI, imagen.getRGB(colJ, renJ));
      imagen.setRGB(colJ, renJ, aux);
      aux = numeros[i];
      numeros[i] = numeros[j];
      numeros[j] = aux;
    }

  }

  public class UpdateWorker extends SwingWorker<BufferedImage, BufferedImage>{

    private BufferedImage referencia;
    private BufferedImage copia;
    private JLabel target;
    int framerate;
    int n;
    String metodo;
    int iteracion;

    public UpdateWorker(BufferedImage master, JLabel target, String archivo, int speed, String algoritmo){
      this.target = target;
      try{
        referencia = ImageIO.read(new File("resource/"+archivo));
        copia = master;
        n = copia.getHeight()*copia.getWidth();
      }catch(Exception e){
        System.err.println(":c Esto no deberia ocurrir");
      }
      framerate = speed; // Indica cada cuantas iteraciones se actualizara la imagen
      metodo = algoritmo;
      iteracion = 0;
    }

    public BufferedImage updateImage(){
      Graphics2D g = copia.createGraphics();
      g.drawImage(copia, 0, 0, null);
      g.dispose();
      return copia;
    }

    @Override
    protected void process(List<BufferedImage> chunks){
      target.setIcon(new ImageIcon(chunks.get(chunks.size() - 1)));
    }

    public void update(){
      for(int i = 0; i < n; i++){
        int indiceDeOriginal = numeros[i];
        int colOriginal = indiceDeOriginal%copia.getWidth();
        int renOriginal = indiceDeOriginal/copia.getWidth();
        int colI = i%copia.getWidth();
        int renI = i/copia.getWidth();
        copia.setRGB(colI, renI, referencia.getRGB(colOriginal, renOriginal));
      }
      publish(updateImage());
    }

    @Override
    protected BufferedImage doInBackground() throws Exception{
      if(metodo.equals("bubble"))
        bubbleSort();
      if(metodo.equals("selection"))
        selectionSort();
      if(metodo.equals("insertion"))
        insertionSort();
      if(metodo.equals("merge"))
        mergeSort();
      if(metodo.equals("quick"))
        quickSort();
      update();
      return null;
    }

//A partir de aquí se aplican los algoritmos de ordenamiento-----------------------------------------------------------------------

    private void bubbleSort(){
      for(int i = 0; i < n-1; i++){
        for(int j = 0; j < n-i-1; j++){
          if(numeros[j] > numeros[j+1])
          swap(j, j+1);
        }
        if(iteracion%framerate == 0) update(); // Actualizamos la interfaz grafica solo si han pasado el numero de iteraciones deseadas
        iteracion = (iteracion+1)%framerate; // Aumentamos el numero de iteraciones
      }
    }

    //Ordenamiento por selección
    private void selectionSort(){
        int min = 0;
        int indMin = 0;
        for(int i=0;i<n-1;i++){
            min = numeros[i];
            indMin = i;
            for(int j=i+1;j<n;j++){
                if(numeros[j]<min){
                    min = numeros[j];
                    indMin = j;
                }
            }
            swap(i,indMin);
            if(iteracion%framerate == 0) update();
            iteracion = (iteracion+1)%framerate;
        }
    }

    //Ordenamiento por inserción
    private void insertionSort(){
        int temp,j;

        for(int i=1;i<n;i++){
            j = i-1;
            temp = numeros[i];
            while(j>=0 && numeros[j]>temp){
                numeros[j+1] = numeros[j];
                j--;
            }
            numeros[j+1] = temp;
            if(iteracion%framerate == 0) update();
            iteracion = (iteracion+1)%framerate;
        }
    }

    /**
     * Algoritmo merge, usado en mergesort.
     * @param x arreglo que recibe
     * @param izq1 índice izquierdo del subarreglo izquierdo.
     * @param izq2 índice derecho del subarreglo izquierdo.
     * @param der1 índice izquierdo del subarreglo derecho.
     * @param der2 índice derecho del subarreglo derecho.
     */
    public void mezcla(int[] x, int izq1, int izq2, int der1, int der2){
        int i,j,k;
        int[] t = new int[der2-izq1+1];

        i = izq1;
        j = der1;
        k = 0;

        //Mueve los elementos menores de x[izq1...izq2] y x[der1...der2] a t.
        while((i<=izq2) && (j<=der2)){
            if(x[i] <= x[j]){
                t[k] = x[i];
                i++;
                k++;
            }else{
                t[k] = x[j];
                j++;
                k++;
            }
        }

        //Mueve los elementos restantes de x[izq1...izq2] a t.
        while(i <= izq2){
            t[k] = x[i];
            i++;
            k++;
        }

        //Mueve los elementos restantes de x[der1...der2] a t.
        while(j <= der2){
            t[k] = x[j];
            j++;
            k++;
        }

        //Regresa los elementos ya mezclados al arreglo original.
        for(i=0;i<t.length;i++){
            x[i+izq1] = t[i];
        }
    }

    /**
     * Algoritmo de mergesort usando índices.
     * @param x arreglo que recibe
     * @param izq índice izquierdo del subarreglo a ordenar
     * @param der índice derecho del subarreglo a ordenar
     */
    public void divide_y_mezcla(int x[], int izq, int der){
        int mitad;
        if(izq < der){
            mitad = (izq+der)/2;
            divide_y_mezcla(x,izq,mitad);
            divide_y_mezcla(x,mitad+1,der);
            mezcla(x,izq,mitad,mitad+1,der);

            if(iteracion%framerate == 0) update();
            iteracion = (iteracion+1)%framerate;
        }
    }

    //Algoritmo merge sort
    private void mergeSort(){
      divide_y_mezcla(numeros,0,n-1);
    }

    /**
     * Función que parte un arreglo
     * X[i]<=X[middle]<X[j]
     * left<=i<=middle<j<=right
     * @param x: Arreglo a partir.
     * @param left: índice izquierdo.
     * @param right: índice derecho.
     * @return middle: índice del pivote al final.
     */
    public int partition(int[] x, int left, int right){
        int l = left;
        int r = right;
        int middle = 0;
        int pivot = x[left];

        while(l<r){
            while(l <= right && x[l] <= pivot){ l++; }
            while(r >= left && x[r] > pivot){ r--; }

            if(l<r){
                swap(x,l,r);
            }
        }

        middle = r;
        swap(x, left, middle);

        return middle;
    }

    public void qs_aux(int[] x, int left, int right){
        if(left < right){
            int mid = partition(x, left, right);
            qs_aux(x, left, mid-1);
            qs_aux(x, mid+1, right);

            if(iteracion%framerate == 0) update();
            iteracion = (iteracion+1)%framerate;
        }
    }

    //Ordenamiento rápido
    private void quickSort(){
      qs_aux(numeros, 0, n-1);
    }

    public void swap(int i, int j){
      int aux = numeros[i];
      numeros[i] = numeros[j];
      numeros[j] = aux;
    }

    public void swap(int[] x, int i, int j){
        int temp = x[i];
        x[i] = x[j];
        x[j] = temp;
    }

  }

}
