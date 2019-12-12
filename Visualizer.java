import java.awt.*;
import java.awt.event.*;
//import java.util.Math;

public class Visualizer extends Frame implements ActionListener,Runnable{
    int[]Arr;
    Button mergeSort,bubbleSort,quickSort;
    String buttonPressed;
    Thread t;
    int in;
    boolean once;

    public Visualizer()
    {
        once=true;
        t=new Thread(this);

        //setting layout
        setLayout(new FlowLayout());

        //creating buttons
        mergeSort=new Button("Merge Sort");
        bubbleSort=new Button("Bubble Sort");
        quickSort=new Button("Quick Sort");

        //Add buttons to frame
        add(mergeSort);
        add(bubbleSort);
        add(quickSort);

        //register action Listener
        mergeSort.addActionListener(this);
        bubbleSort.addActionListener(this);
        quickSort.addActionListener(this);

        addWindowListener(new MyWindowAdaptor());
    }




    //implementing action listener
    public void actionPerformed(ActionEvent ae){
        buttonPressed=ae.getActionCommand();
        if(t.isAlive()==true)
        {
            t.stop();
        }
        t=null;
        t=new Thread(this);
        Arr=new int[20];
        for(int i=0;i<Arr.length;i++){
            Arr[i]=(int)(Math.random()*100+1);
            Arr[i]+=10;
        }
        //repaint();
        t.start();
    }



    //run method of thread
    public void run()
    {
        in=-1;
        if(buttonPressed=="Merge Sort") {
            setTitle("Merge Sort");
            MergeSortFunction(Arr, 0, 19);
        }
        else if(buttonPressed=="Bubble Sort"){
            setTitle("Bubble Sort");
            bubbleSort(Arr);
        }
        else if(buttonPressed=="Quick Sort"){
            setTitle("Quick Sort");
            QuickSortFunc(Arr,0,19);
        }

    }




    //----------------------bubble sort------------
    void bubbleSort(int arr[])
    {
        int n = arr.length;
        in=-1;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n - i - 1; j++)
                if (arr[j] > arr[j + 1]) {
                    in=j+1;
                    // swap arr[j+1] and arr[i]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    repaint();
                }
        }
    }
    //----------------------bubble sort end---------



    //---------------quick sort------------------

    int partition(int arr[], int low, int high)
    {
        int pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than the pivot
            if (arr[j] < pivot)
            {
                i++;

                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;
        try{
            Thread.sleep(1000);
        }
        catch (Exception e){
        }
        in=i+1;
        repaint();

        return i+1;
    }

    void QuickSortFunc(int arr[], int low, int high)
    {
        if (low < high)
        {
            int pi = partition(arr, low, high);

            QuickSortFunc(arr, low, pi-1);
            QuickSortFunc(arr, pi+1, high);
        }
    }
    //----------------quick sort end-----------



    // --------------------merge sort start
    void merge(int arr[], int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int [n1];
        int R[] = new int [n2];

        for (int i=0; i<n1; ++i)
            L[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];


        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2)
        {
            if (L[i] <= R[j])
            {
                arr[k] = L[i];
                i++;
            }
            else
            {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
        try{
            Thread.sleep(1000);
        }
        catch (Exception e){
        }
        in=m;
        repaint();
    }
    void MergeSortFunction(int arr[], int l, int r)
    {
        if (l < r)
        {
            int m = (l+r)/2;

            MergeSortFunction(arr, l, m);
            MergeSortFunction(arr , m+1, r);

            merge(arr, l, m, r);
        }

    }
    //---------------------merge sort end




    public void paint(Graphics g){

        if(once){
            g.drawString("Sorting Visualizer",100,200);
            g.drawString("Press above on any of the buttons to start",50,215);
            once=false;
        }
        else {
            //Draw lines
            Color black = new Color(0, 0, 0);
            Color red = new Color(255, 0, 0);

            // beep sound
            Toolkit.getDefaultToolkit().beep();

            //Drawing Lines
            for (int i = 0; i < Arr.length; i++) {
                if (i == in) {
                    g.setColor(red);
                } else {
                    g.setColor(black);
                }
                g.drawLine(i * 15 + 4, 400, i * 15 + 4, 400 - 3 * Arr[i]);
            }
        }
    }




    public static void main(String[] args){
        Visualizer app=new Visualizer();
        app.setSize(new Dimension(307,400));

        //Making frame fixed size
        app.setResizable(false);

        //Making it load to centre
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        app.setLocation(dim.width/2-app.getSize().width/2, dim.height/2-app.getSize().height/2);

        app.setTitle("Sorting Visualizer");
        app.setVisible(true);
    }
}

class MyWindowAdaptor extends WindowAdapter{
    public void windowClosing(WindowEvent we){
        System.exit(0);
    }
}