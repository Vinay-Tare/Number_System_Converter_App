package com.example.number_system_converter_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    double con_fromhex(int b,String s)
    {
        int a=0;
        double n=0;
        int len=s.length();
        char hex[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

        for(int j=len-1;j>=0;j--)
        {
            for(int i=0;i<16;i++)
            {
                if(s.charAt(j)==hex[i])
                {
                    a=i;
                    n=(double)((a)*Math.pow(16,(len-1-j)))+n;
                }
            }
        }
        return R_B(10,b,n);
    }

    String con_tohex(int p,double n)
    {
        int a=0,r=p;
        double x=0;
        for(int i=0;n!=0;i++)
        {
            a=(int)n%10;
            x=((a)*Math.pow(r,i))+x;
            n=(int)n/10;
        }
        double fracp=Base_Point(r,2,n);
        n=x;
        x=0;
        String dec_hex_fracp="\0";
        a=0;
        double Hex[]={0.0000,0.0001,0.0010,0.0011,0.0100,0.0101,0.0110,0.0111,0.1000,0.1001,0.1010,0.1011,0.1100,0.1101,0.1110,0.1111};
        for(int i=0;i<Hex.length;i++)
        {
            if(fracp==Hex[i])
            {
                char hex[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
                while(i!=0)
                {
                    a=(int)i%16;
                    dec_hex_fracp=hex[a]+dec_hex_fracp;
                    i=(int)i/16;
                }
            }
        }

        char hex[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        String dec_hex="\0";
        while(n!=0)
        {
            a=(int)n%16;
            dec_hex=hex[a]+dec_hex;
            n=(int)n/16;
        }
        return dec_hex+dec_hex_fracp;
    }

    double R_B(int p,int q,double n)
    {
        int a=0,r=p,b=q;
        double x=0;
        double fracp=Base_Point(r,b,n);
        for(int i=0;n!=0;i++)
        {
            a=(int)n%10;
            x=(int)((a)*Math.pow(r,i))+x;
            n=n/10;
        }
        n=x;
        x=0;

        for(int i=0;n!=0;i++)
        {
            a=(int)n%b;
            x=(int)((a)*Math.pow(10,i))+x;
            n=n/b;
        }
        return x+fracp;
    }

    double Base_Point(int bs1,int bs2,double num)
    {
        int a=0,count=0;
        double x=0,t=num;
        while(num!=((int)num))
        {
            count++;
            num=num*10;
        }
        num=t;
        num=num*(Math.pow(10,count));

        for (int i=-count;num!=0;i++)
        {
            a=(int)num%10;
            x=((a)*Math.pow(bs1,i)+x);
            num=(int)num/10;
        }
        num=x;

        int intp=(int)num;
        double fracp=num-intp;
        int i;
        x=0;
        for(i=0;fracp!=0;i++)
        {
            fracp=fracp*2;
            if(fracp>1)
            {
                fracp=fracp-1 ;
                x=((1)*Math.pow(10,i))+x;
            }
            if(fracp==1)
            {
                fracp=0;
                x=((1)*Math.pow(10,i))+x;
            }
            else
            {
                x=((0)*Math.pow(10,i))+x;
            }
        }
        x=x/(Math.pow(10, i));
        num=x;

        if(bs2==2)
        {
            return x;
        }
        else if(bs2==8)
        {
            a=0;
            double Oct[]={0.000,0.001,0.010,0.011,0.100,0.101,0.110,0.111};
            for(i=0;i<Oct.length;i++)
            {
                if(x==Oct[i])
                {
                    x=(double)i/10;
                    return x;
                }
            }
        }
        else if(bs2==10)
        {
            a=0;count=0;
            x=0;t=num;
            while(num!=((int)num))
            {
                count++;
                num=num*10;
            }
            num=t;
            num=num*(Math.pow(10,count));

            for (i=-count;num!=0;i++)
            {
                a=(int)num%10;
                x=((a)*Math.pow(2,i)+x);
                num=(int)num/10;
            }
            return x;
        }
        return x;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onButtonClick(View v){
        EditText e1=(EditText) findViewById(R.id.editText);
        EditText e2=(EditText) findViewById(R.id.editText2);
        EditText e3=(EditText) findViewById(R.id.editText3);
        TextView t1=(TextView) findViewById(R.id.textView);
        if((e1.getText().toString().length()==0)||(e2.getText().toString().length()==0)||(e3.getText().toString().length()==0))
        {
            t1.setTextColor(Color.RED);
            t1.setText("     Please Enter A No. First!!");
        }
        else
        {   t1.setTextColor(Color.GREEN);
            int base1=Integer.parseInt(e2.getText().toString());
            int base2=Integer.parseInt(e3 .getText().toString());
            if (base1 == 16) {
                String s = e1.getText().toString();
                t1.setText(" Result:" + con_fromhex(base2, s));
            } else if (base2 == 16) {
                double num = Double.parseDouble(e1.getText().toString());
                String s = con_tohex(base1, num);
                t1.setText(" Result:" + s);
            } else {
                double num = Double.parseDouble(e1.getText().toString());
                double res = R_B(base1, base2, num);
                t1.setText(" Result:" + Double.toString(res));
            }
        }
    }
}
