import java.util.Scanner;
public class Driver{
    public static void main(String args[]){
        int numTestCases;
        Scanner sc = new Scanner(System.in);
        numTestCases = sc.nextInt();
        long starttime = System.currentTimeMillis();
        while(numTestCases-->0){
            int size;
            size = sc.nextInt();
            A2DynamicMem obj = new A2DynamicMem(size,1);
            int numCommands = sc.nextInt();
            while(numCommands-->0) {
                String command;
                command = sc.next();
                int argument;
                argument = sc.nextInt();
                int result = -5;
                boolean normal = true;
                switch (command) {
                    case "Allocate":
                        result = obj.Allocate(argument);
                        break;
                    case "Free":
                        result = obj.Free(argument);
                        break;
                    case "Defragment":
                        obj.Defragment();
                        normal=false;
                        break;
                    default:
                        break;
                }
                if(normal){
                System.out.println(result);}
            }
            
        }
        long endtime=System.currentTimeMillis();
        System.out.println("Total runtime "+ (endtime-starttime));
    }
}
