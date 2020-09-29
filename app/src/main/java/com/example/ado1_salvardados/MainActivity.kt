package com.example.ado1_salvardados

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.reflect.typeOf
import kotlin.text.toDouble as toDouble

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Espécie e vetor que irá armazenas os dados na memoria secundaria do dispositivo
        val areaPersistencia = getSharedPreferences("areaPersistencia", Context.MODE_PRIVATE)

        //limpar campos
        btLimpar.setOnClickListener { v: View? ->
            txtNomeProduto.text.clear()
            txtPrecoCusto.text.clear()
            txtPrecoVenda.text.clear()
            txtLucroPrejuizo.text.clear()
        }

        //gravar dados
        btSalvar.setOnClickListener { v: View? ->
            if(txtNomeProduto.text.isNotEmpty()&&txtPrecoCusto.text.isNotEmpty()&&txtPrecoVenda.text.isNotEmpty()){

                //Metodo para resgatar valores dos campos
                var precoCusto = txtPrecoCusto.text.toString()
                var precoVenda = txtPrecoVenda.text.toString()

                //Metodo para tratar erro caso usuario digite valores decimais com " , "
                precoCusto.replace(",",".")
                precoVenda.replace(",",".")

                try {

                    var custoProduto = precoCusto.toDouble()
                    var vendaProduto = precoVenda.toDouble()


                    areaPersistencia.edit().putString((txtNomeProduto.text.toString()+"1"),txtPrecoCusto.text.toString()).apply()
                    areaPersistencia.edit().putString((txtNomeProduto.text.toString()+"2"),txtPrecoVenda.text.toString()).apply()
                    Toast.makeText(this,"Anotação Salva", Toast.LENGTH_SHORT).show()
                }catch (e: NumberFormatException){
                    Toast.makeText(this, "Valor incorreto", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this,"Nome da anotação inexistente", Toast.LENGTH_SHORT).show()
            }
        }

        btAbrir.setOnClickListener { v: View? ->

            var lucroPrejuizo=0.001

            //variavel para remover casas decimais sem utilização
            var lucroPrejuizoConvertida=0.001


            if(txtNomeProduto.text.isNotEmpty()){

                var texto1 = areaPersistencia.getString((txtNomeProduto.text.toString()+"1"),"")
                var texto2 = areaPersistencia.getString((txtNomeProduto.text.toString()+"2"),"")

                //Variaveis para converter valores recebidos em String, nas variaveis acima eles não
                //são convertidos em String e sim em String?, essa pode não ser a melhor solucao para o problema
                var texto3 = texto1.toString()
                var texto4 = texto2.toString()

                try {
                    //Conversão dos valores para double para poder realizar operação de subtração
                    var custoProduto = texto3.toDouble()
                    var vendaProduto = texto4.toDouble()
                    lucroPrejuizo = vendaProduto-custoProduto

                    //Metodo para remover casas decimais sem utilização
                    //val dec = DecimalFormat("#,###.##")
                    //var lucroPrejuizoConvertida = dec.format(lucroPrejuizo)


                }catch (e: NumberFormatException){
                    Toast.makeText(this, "Valor incorreto", Toast.LENGTH_SHORT).show()
                }


                if(texto1.isNullOrEmpty()&&texto2.isNullOrEmpty()){
                    Toast.makeText(this,"Anotação Vazia ou inexistente",Toast.LENGTH_SHORT).show()
                }
                else{
                    txtPrecoCusto.setText(texto1)
                    txtPrecoVenda.setText(texto2)
                    txtLucroPrejuizo.setText(lucroPrejuizo.toString())

                    Toast.makeText(this,"Anotação Carregada com Sucesso",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,"Nome da anotação vazia ou inexistente",Toast.LENGTH_SHORT).show()
            }

        }

    }
}