package com.bytedance.jstu.demo.lesson3.masterdetail;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bytedance.jstu.demo.R;


public class ItemDetailActivity extends FragmentActivity {
	ItemDetailFragment fragmentItemDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		// 从Intent里拿出item参数
		Item item = (Item) getIntent().getSerializableExtra("item");
		if (savedInstanceState == null) {
			fragmentItemDetail = ItemDetailFragment.newInstance(item);
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.flDetailContainer, fragmentItemDetail);
			ft.commit();
		}
	}

}
