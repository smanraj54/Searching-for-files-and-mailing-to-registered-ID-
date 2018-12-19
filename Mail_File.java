package mali_file;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

class Node
{
    File file;
    String name;
    Node next;
}

public class Mail_File {
   static Node main_address;
   static File Root=null;
  // static Node mm=null;
public static void main(String ar[])throws Exception
{
    // Add the location to save after mailing the file... these files will not be mailed again
    String add="HNo.128_Family";
    //folders or files with these names will be mailed
    String ss="manrajdoc , harvinderdoc ,inderjitdoc,bhavneetdoc,papadoc,mummidoc,chunudoc,gudiadoc,HNo.128_Family,manudoc, chunudoc";
    //Runtime addition of any other folder is also allowed
    String ss1=JOptionPane.showInputDialog("Enter any other foder(seprated with , if multiple) you want to search other than :\n\n"+ss+"\n");
    if(ss1!=null && !ss1.equals("") && ss1.length()>2){if(ss!=null && !ss.equals(""))ss=ss+", "+ss1;else ss=ss1;}
    ss=ss.replaceAll(" ","" );ss=ss.replaceAll("@","," );
    ss=ss.toLowerCase();
    File f[]=File.listRoots();
    Mail_File mf=new Mail_File();
    Node m=null;
    File root=null;
    for(int t=0;t<f.length;t++)
    {
        m=mf.getFolder( m,  f[t],ss);
        //mm=mf.getFolder( mm,  f[t]);
        //try{}catch(Exception ee){}
        if(f[t].getFreeSpace()>9999999 && (root==null || !root.getName().equals(add))){root=f[t];System.out.println("\n\n\n    manu sure     "+root+"\n\n\n");}
    }
        Root=root;
        System.out.println("\n\n            Existing Files : ");
        mf.Print(main_address);
        System.out.println("\n\n            New Files : ");
        long total=mf.Print(m);JOptionPane.showMessageDialog(null,total);
           System.out.println("\n\n\n    manu sure Final check    "+Root+"\n\n\n");
        if(!Root.getName().equals(add))
        {
            File rr=new File(Root.getAbsolutePath()+"\\"+add);
            if(rr.mkdir())System.out.println("Directory is made");
            Root=rr;
        }
        while(m!=null){
        Mail_sending ms=new Mail_sending();
        //JOptionPane.showMessageDialog(null,Root.getAbsolutePath());
        if(!mf.checkFile(m,main_address))
        {boolean bb=false;
        bb=ms.mail_sending(m);
        File dir[]=root.listFiles();
        File d=null;
        for(int t=0;t<dir.length;t++)
        {
            if(dir[t].getName().toLowerCase().equals(m.name.toLowerCase())){d=dir[t];break;}
        }
            if(d==null)
            {
                d=new File(Root.getAbsolutePath()+"\\"+m.name.toUpperCase());
            if(d.mkdir())System.out.println("Directory is made2");
            }
            Files.copy(m.file.toPath(),(new File(d.getPath()+"\\"+m.file.getName())).toPath(),StandardCopyOption.REPLACE_EXISTING);
            if(bb)m.file.delete();
        }
        m=m.next;
        }
        JOptionPane.showMessageDialog(null,"Done All mailing Process");
}

// recursion is used to find the folder
public Node getFolder(Node mm, File f, String ss )throws Exception
{
    System.out.println(f);
    Mail_File mf=new Mail_File();
    if(f.isDirectory())
    {
        File ff[]=f.listFiles();
        for(int t=0;t<ff.length;t++)
        {
            try{
                String yy="",s[]=ss.split(","),add="HNo.128_Family";
                for(int tt=0;tt<s.length;tt++)if(ff[t].isDirectory() && ff[t].getName().toLowerCase().contains(s[tt]))
                {
                    yy=s[tt];
                    if((ff[t].getName().toLowerCase()).equals(add.toLowerCase()))
                    {Mail_File.main_address=mf.get_Files(ff[t],add,Mail_File.main_address);Mail_File.Root=ff[t];}
                    else mm=mf.get_Files(ff[t],yy,mm);
                    //break;
                }
                if(!(ff[t].getName().toLowerCase()).equals(add.toLowerCase()))mm=mf.getFolder(mm,ff[t],ss);
            }catch(Exception ee){}
        }
    }
    return mm;
}
public Node get_Files(File f, String ss, Node m)throws Exception
{try{
    if(f.isDirectory())
    {
        File ff[]=f.listFiles();
        for(int t=0;t<ff.length;t++)
        {
            m=get_Files(ff[t],ss,m);
        }
    }
    else
    {
     Mail_File mf=new Mail_File();
     m=mf.insert(m,f,ss);
    return m;
    }
}
catch(Exception ee){}return m;
}

public Node insert(Node m,File f,String ss)
{
    System.out.println(ss+"         "+f);
    Node temp=new Node();
    Node temp2=m;
    temp.file=f;temp.name=ss;temp.next=null;
    if(m==null)return temp;
    while(temp2.next!=null){temp2=temp2.next;}
    temp2.next=temp;
    return m;
}
public long Print(Node m)
{
    long tt=0;
    if(m==null)System.out.println("Empty");
    Node temp=m;
    while(temp!=null)
            {tt++;System.out.println("\n"+temp.name+"     "+temp.file+"\n");temp=temp.next;}
    return tt;
}

public boolean checkFile(Node m, Node list)
{
    Node temp=list;
    if(list==null)return false;
    while(temp!=null){if(temp.file.getName().toLowerCase().equals(m.file.getName().toLowerCase()))return true;temp=temp.next;}
    return false;
}
}