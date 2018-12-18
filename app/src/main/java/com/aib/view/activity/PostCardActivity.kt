package com.aib.view.activity

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.text.TextUtils
import android.view.View
import android.widget.TextView

import com.aib.adapter.PostCardAdapter
import com.aib.utils.Constants
import com.aib.entity.CardTagEntity
import com.aib.viewmodel.PostCardVm
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityPostCardBinding
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.api.widget.Widget
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import top.zibin.luban.Luban
import java.io.File
import java.util.*

/**
 * 发布帖子
 *
 * 其实还有很多优化的地方，可以考虑了解好每种集合的优缺点，利用集合的优缺点进行实现这个9宫格会更方便一些
 *
 * 标题不能为空，标题长度在5-30个字， 内容长度在20-500个字，图片数量在0-4张
 */
class PostCardActivity : BaseActivity<ActivityPostCardBinding>() {

    private var data = ArrayList<Any>() //保存图片路径

    var checkFile = ArrayList<AlbumFile>() //保存已选择文件数据

    private var tagColor: String? = null    //标签颜色值

    private var tagName: String? = null

    private lateinit var vm: PostCardVm

    private lateinit var dialog: ProgressDialog

    override fun getResId(): Int = R.layout.activity_post_card

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0,BarUtils.getStatusBarHeight(),0,0)

        vm = getViewModel(PostCardVm::class.java)

        binding.presenter = Presenter()

        dialog = ProgressDialog(this@PostCardActivity)
        dialog.setMessage("提交中...")

        data.add(R.drawable.ic_add_img)

        val adapter = PostCardAdapter()
        binding.rv.adapter = adapter

        adapter.passData(data)
        adapter.notifyDataSetChanged()

        /**
         * Rv点击事件
         */
        adapter.setOnItemClickListener(object : PostCardAdapter.OnItemClickListener() {
            override fun itemDelete(position: Int) {
                data.removeAt(position)
                if (data.size == 3) {
                    if (!data.get(data.size - 1).equals(R.drawable.ic_add_img)) {
                        data.add(R.drawable.ic_add_img)
                    }
                }
                checkFile.removeAt(position)
                adapter.passData(data)
                adapter.notifyDataSetChanged()
            }

            override fun itemClick(position: Int) {
                if (position == data.size - 1 && data.get(data.size - 1) == R.drawable.ic_add_img) {
                    Album.image(this@PostCardActivity)
                            .multipleChoice()
                            .widget(Widget.newDarkBuilder(this@PostCardActivity)
                                    .title("相册")
                                    .statusBarColor(resources.getColor(R.color.colorAccent))
                                    .toolBarColor(resources.getColor(R.color.colorAccent))
                                    .navigationBarColor(resources.getColor(R.color.colorAccent))
                                    .bucketItemCheckSelector(Color.WHITE, resources.getColor(R.color.colorAccent))
                                    .mediaItemCheckSelector(Color.WHITE, resources.getColor(R.color.colorAccent))
                                    .build())
                            .camera(true)
                            .columnCount(4)
                            .selectCount(4)
                            .checkedList(checkFile)
                            .filterSize(null)
                            .filterMimeType(null)
                            .afterFilterVisibility(false)
                            .onResult({
                                checkFile.clear()
                                checkFile.addAll(it)

                                data.clear()
                                data.add(R.drawable.ic_add_img)

                                for (i in it.indices) {
                                    if (!data.get(i).equals(it.get(i).path)) {
                                        data.add(data.size - 1, it.get(i).path)
                                    }
                                }

                                if (data.size == 5) {
                                    data.removeAt(data.size - 1)
                                }

                                adapter.passData(data)
                                adapter.notifyDataSetChanged()
                            })
                            .onCancel({
                                ToastUtils.showShort("取消")
                            })
                            .start()
                } else {
                    ToastUtils.showShort("不是最后一个")
                }
            }
        })

        //获取发帖标签列表
        vm.tagList().observe(this, android.arch.lifecycle.Observer {
            if (it!!.code == 1) {
                binding.fl.adapter = object : TagAdapter<CardTagEntity>(it.data) {
                    override fun getView(parent: FlowLayout, position: Int, t: CardTagEntity): View {
                        val view = View.inflate(parent.context, R.layout.post_card_tag, null)
                        val tv = view.findViewById<TextView>(R.id.tv)
                        tv.text = t.tag
                        return tv
                    }

                    override fun onSelected(position: Int, view: View?) {
                        super.onSelected(position, view)
                        val tv: TextView = view as TextView
                        tv.setBackgroundColor(Color.parseColor(it.data[position].tagColor))
                        tagName = tv.text.toString().trim()
                        tagColor = it.data[position].tagColor
                    }

                    override fun unSelected(position: Int, view: View?) {
                        super.unSelected(position, view)
                        val tv: TextView = view as TextView
                        tv.setBackgroundColor(resources.getColor(R.color.color_c3c3c3))
                        tagName = null
                        tagColor = null
                    }
                }
            } else {
                ToastUtils.showShort(it.msg)
            }
        })
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        fun post(view: View) {
            val getTitle = binding.etTitle.text.toString().trim()
            val getContent = binding.etContent.text.toString().trim()

            if (TextUtils.isEmpty(getTitle)) {
                ToastUtils.showShort("请输入标题")
                return
            }

            if (getTitle.length !in 5..30) {
                ToastUtils.showShort("字数不达标")
                return
            }

            if (TextUtils.isEmpty(getContent)) {
                ToastUtils.showShort("请输入内容")
                return
            }

            if (getContent.length !in 50..500) {
                ToastUtils.showShort("字数不达标")
                return
            }

            dialog.show()
            //key：作为请求体中Content-Disposition里面name的值
            //value：HTTP请求格式报文的值
            val params = ArrayMap<String, RequestBody>()
            params.put("title", RequestBody.create(MediaType.parse("text/plain"), getTitle))    //参数1
            if (!TextUtils.isEmpty(tagName)) {
                params.put("tag", RequestBody.create(MediaType.parse("text/plain"), tagName))  //参数2
            }
            params.put("content", RequestBody.create(MediaType.parse("text/plain"), getContent))  //参数3
            params.put("token", RequestBody.create(MediaType.parse("text/plain"), SPUtils.getInstance().getString(Constants.TOKEN)))    //参数4
            if (!TextUtils.isEmpty(tagColor)) {
                params.put("tagColor", RequestBody.create(MediaType.parse("text/plain"), tagColor))   //参数5
            }
            //以下是构建文件请求体对象，放到ArrayMap集合中
            data.removeAt(data.size - 1)
            Flowable.just(data)
                    .observeOn(Schedulers.io())
                    .map(object : Function<ArrayList<Any>, List<File>> {
                        override fun apply(t: ArrayList<Any>): List<File> {
                            return Luban.with(this@PostCardActivity).load(t).get()
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it.isNotEmpty()) {
                            for (item in it) {
                                params.put("files" + "\"; filename=\"" + item!!.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), item))
                            }
                        }
                        vm.postCard(params).observe(this@PostCardActivity, android.arch.lifecycle.Observer {
                            if (it!!.code == 1) {
                                ToastUtils.showShort(it.msg)
                                setResult(Activity.RESULT_OK)
                                finish()
                            } else {
                                ToastUtils.showShort(it.msg)
                            }

                            dialog.dismiss()
                        })
                    }, {

                    })
        }
    }
}
