package com.bytedance.jstu.demo.lesson3.masterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bytedance.jstu.demo.R;

// 实现 OnItemSelectedListener 监听
public class ItemsListActivity extends FragmentActivity implements ItemsListFragment.OnItemSelectedListener {
	// 判断横竖屏
	private boolean isTwoPane = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
		determinePaneLayout();
	}

	private void determinePaneLayout() {
		FrameLayout fragmentItemDetail = findViewById(R.id.flDetailContainer);
		if (fragmentItemDetail != null) {
			isTwoPane = true;
			ItemsListFragment fragmentItemsList = 
					(ItemsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
			// 打开内部ListView的激活状态
			fragmentItemsList.setActivateOnItemClick(true);
		}
	}

	/**
	 * 当Item被选择的时候回调
	 * @param item Item的内容对象
	 */
	@Override
	public void onItemSelected(Item item) {
		if (isTwoPane) { // single activity with list and detail
			// Replace frame layout with correct detail fragment
			ItemDetailFragment fragmentItem = ItemDetailFragment.newInstance(item);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flDetailContainer, fragmentItem);
			ft.commit();
		} else { // separate activities
			// launch detail activity using intent
			Intent i = new Intent(this, ItemDetailActivity.class);
			// 传入被选择item的信息
			i.putExtra("item", item);
			startActivity(i);
		}
	}

}
