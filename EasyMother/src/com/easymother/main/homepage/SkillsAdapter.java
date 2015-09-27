package com.easymother.main.homepage;

import java.util.List;

import com.easymother.bean.Skill;
import com.easymother.main.R;
import com.easymother.utils.CommonAdapter;
import com.easymother.utils.ViewHolder;

import android.content.Context;
import android.widget.TextView;

public class SkillsAdapter extends CommonAdapter<Skill> {

	protected SkillsAdapter(Context context, List<Skill> list, int resource) {
		super(context, list, resource);
	}

	@Override
	public void setDataToItem(ViewHolder holder, Skill t) {
		TextView name=holder.getView(R.id.skill_name);
		if (t.getName()!=null) {
			name.setText(t.getName());
		}else {
			name.setText("");
		}
		
	}

}
