package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

//handle any user interaction
class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //delete feature
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //1. remove the item from the list
                listOfTasks.removeAt(position)
                //2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                //persist feature
                saveItems()
            }

        }



        //1. Let's detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            //Code in here is going to be executed when the user clicks on a butotn
//            Log.i("kt", "User clicked on button")
//
//        }

//        listOfTasks.add("Do laundry")
//        listOfTasks.add("Go for a walk")


        //persist feature, no need to hard code listOfTasks anymore
        loadItems()

        //Bind the adapter to MainActivity.kt
        //Look up recyclerview in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //Create adapter passing in the sample user data
         adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        //Attach the adapter to the recyclerview to populate item
        recyclerView.adapter = adapter
wqgi

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Adding feature
        //set up the button and input field, so that the user can
       //enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener{
            //1.Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            //2.Add the string to our list of tasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been apdated
            adapter.notifyItemChanged(listOfTasks.size -1)

            //3. Reset text filed
            inputTextField.setText("")

            //persist feature
            saveItems()
        }

    }
    // Persist feature
    //save the data that the user has inputted
    //save data by writing and reading from a file

    // Get the file we need
    fun getDataFile() : File {
        // Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }
    //Load the items by reading every line in the data file
    fun loadItems(){
        try{
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())

        } catch (ioException:IOException){

            ioException.printStackTrace()
        }

    }

    //Save items by writing into our data file
    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException : IOException){
            ioException.printStackTrace()
        }

    }
}