import java.util.Scanner;
public class String
{
	public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
    System.out.print("enter the odd character string : ");
    String s=sc.nextLine();
    int l=s.length();
    for(int i=1; i<=l; i++)
      {
          for(int j=1; j<=l; j++)
          if(i==j || i+j==l+1)
          System.out.print(s.charAt(i-1)+" ");
          else
            System.out.print("  ");
            System.out.println();
}
	}
}import java.util.Scanner;
public class Main
{
	public static void main(String[] args) {
    Scanner sc=new Scanner(System.in);
    System.out.print("enter the odd character string : ");
    String s=sc.nextLine();
    int l=s.length();
    for(int i=1; i<=l; i++)
      {
          for(int j=1; j<=l; j++)
          if(i==j || i+j==l+1)
          System.out.print(s.charAt(i-1)+" ");
          else
            System.out.print("  ");
            System.out.println();
}
	}
}