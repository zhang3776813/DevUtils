package afkt.demo.ui.activity

import afkt.demo.R
import afkt.demo.base.app.BaseActivity
import android.os.Bundle
import afkt.demo.databinding.ActivityMainBinding

/**
 * detail: Main Activity
 * @author Ttt
 */
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun baseContentId(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.title = "DataBinding Title"
    }
}