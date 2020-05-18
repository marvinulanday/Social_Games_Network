package com.stucom.socialgamesnetwork.CustomExpandableListView;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.stucom.socialgamesnetwork.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groups;
    private HashMap<String, List<String>> items;
    private HashMap<String, Set<String>> itemsChecked;

    public ExpandableListAdapter(Context context, List<String> groups,
                                 HashMap<String, List<String>> items) {
        this.context = context;
        this.groups = groups;
        this.items = items;
        this.itemsChecked = new HashMap<>();
        for (String group : this.groups) {
            itemsChecked.put(group, new HashSet<String>());
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.items.get(this.groups.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_filter_item, null);
        }
        CheckBox checkBoxItem = (CheckBox) convertView.findViewById(R.id.lblListItem);
        checkBoxItem.setText(childText);
        checkBoxItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckBox cb = (CheckBox) v;
                if (cb.isChecked()) {
                    itemsChecked.get(getGroup(groupPosition)).add(cb.getText().toString());
                } else {
                    itemsChecked.get(getGroup(groupPosition)).remove(cb.getText().toString());
                }
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.items.get(this.groups.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_filter_group, null);
        }

        TextView txtViewGroup = (TextView) convertView.findViewById(R.id.lblListHeader);
        txtViewGroup.setTypeface(null, Typeface.BOLD);
        txtViewGroup.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public HashMap<String, Set<String>> getItemsChecked() {
        return itemsChecked;
    }
}

