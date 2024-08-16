package com.example.proyectoiib_kronos

import android.content.DialogInterface
import android.content.Intent
import android.media.metrics.Event
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val currentDate: LocalDate = LocalDate.now()
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale("es"))
    private val formattedDate: String = currentDate.format(formatter)
    private var eventsList: ArrayList<EventEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarDias()
        inicializarMes()

        Database.data = SqliteHelper(this)

        val btnCategory=findViewById<ImageView>(R.id.btn_add_category)
        btnCategory.setOnClickListener{
            irActividad(CategoryForm::class.java)
        }

        val btnEvent=findViewById<ImageView>(R.id.btn_add_task)
        btnEvent.setOnClickListener{
            irActividad(EventForm::class.java)
        }

        this.eventsList = Database.data!!.getEventsByDate(formattedDate)

        //Colocar datos en Lista
        val listView = findViewById<ListView>(R.id.list_events)
        val adaptador = object : ArrayAdapter<EventEntity>(
            this,
            android.R.layout.simple_list_item_1,
            this.eventsList
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val inflater = LayoutInflater.from(context)
                val eventView = inflater.inflate(R.layout.event_view, null, true)

                val textHour = eventView.findViewById<TextView>(R.id.text_hour)
                val textTitle = eventView.findViewById<TextView>(R.id.text_title)
                val textCatefory = eventView.findViewById<TextView>(R.id.category_text)
                val event = getItem(position)
                textHour.text = event?.hour
                textTitle.text = event?.title
                textCatefory.text = Database.data!!.findCategoryByID(event?.categoryId)?.name
                return eventView
            }
        }
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)
    }

    private fun inicializarMes(){
        val currentDate = LocalDate.now()

        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("es"))
        val formattedDate = currentDate.format(formatter)

        findViewById<TextView>(R.id.text_month_year).text = formattedDate.capitalize(Locale.ROOT)
    }

    private fun inicializarDias(){

        val (diasList, numerosList ) = getDatesOfCurrentMonth()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_days)
        val adaptador = AdapterDays(numerosList, diasList)
        recyclerView.adapter = adaptador
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adaptador.notifyDataSetChanged()
    }

    private fun getDatesOfCurrentMonth(): Pair<ArrayList<String>, java.util.ArrayList<Int>> {
        val diasList = arrayListOf<String>()
        val numerosList = arrayListOf<Int>()

        val currentMonth = LocalDate.now().month
        val currentYear = LocalDate.now().year

        val firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1)
        val lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1)

        var currentDate = firstDayOfMonth

        while (currentDate <= lastDayOfMonth) {
            val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("es"))
            val dayOfMonth = currentDate.dayOfMonth

            diasList.add(dayOfWeek)
            numerosList.add(dayOfMonth)

            currentDate = currentDate.plusDays(1)
        }

        return Pair(diasList, numerosList)
    }

    private fun irActividad( clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    //Funciones para el menu
    var index = -1

    override fun onCreateContextMenu(
        menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //opciones menu
        menuInflater.inflate(R.menu.menu_event, menu)

        //opcion seleccionada
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        index = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mi_eliminar->{
                abrirDialogo(index)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun abrirDialogo(index:Int){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Eliminar evento?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                Database.data!!.deleteEvent(this.eventsList[index].id)

                val listView = findViewById<ListView>(R.id.list_events)
                this.eventsList.clear()
                this.eventsList = Database.data!!.getEventsByDate(formattedDate)
                val adaptador = object : ArrayAdapter<EventEntity>(
                    this,
                    android.R.layout.simple_list_item_1,
                    this.eventsList
                ) {
                    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                        val inflater = LayoutInflater.from(context)
                        val eventView = inflater.inflate(R.layout.event_view, null, true)

                        val textHour = eventView.findViewById<TextView>(R.id.text_hour)
                        val textTitle = eventView.findViewById<TextView>(R.id.text_title)
                        val textCatefory = eventView.findViewById<TextView>(R.id.category_text)
                        val event = getItem(position)
                        textHour.text = event?.hour
                        textTitle.text = event?.title
                        textCatefory.text = Database.data!!.findCategoryByID(event?.categoryId)?.name
                        return eventView
                    }
                }
                listView.adapter = adaptador
                adaptador.notifyDataSetChanged()
            }
        )
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }
}