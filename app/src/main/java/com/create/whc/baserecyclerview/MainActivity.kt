package com.create.whc.baserecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.create.whc.baserecyclerview.baseadapter.getItemManager
import com.create.whc.baserecyclerview.baseadapter.withItems
import com.create.whc.baserecyclerview.item.ImageItem
import com.create.whc.baserecyclerview.item.buttonItem
import com.create.whc.baserecyclerview.item.imageItem
import com.create.whc.baserecyclerview.item.singleText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            withItems {

                this.add(
                        ImageItem()
                )

                repeat(10){
                    singleText("this is a test $it")
                }
                buttonItem("add Item"){
                    setOnClickListener {
                        addItem()
                    }
                }
            }
        }
    }

    fun addItem(){
        recyclerview.getItemManager().refreshAll{
            repeat(10) {
                singleText("this is a test ${it+10}")
            }
        }
    }

}
