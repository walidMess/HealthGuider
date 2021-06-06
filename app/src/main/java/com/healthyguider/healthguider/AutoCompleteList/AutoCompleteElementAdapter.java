package com.healthyguider.healthguider.AutoCompleteList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthyguider.healthguider.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wezzy on 03/05/2018.
 */

public class AutoCompleteElementAdapter extends ArrayAdapter<Element> {

    //private Context mContext;
    //private List<Element> ElementList = new ArrayList<>();
    //private HashMap<String,Element> ElementList = new HashMap<String, Element>();


    private LayoutInflater layoutInflater;
    List<Element> Elemnts;

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Element)resultValue).getOption();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Element> suggestions = new ArrayList<Element>();
                for (Element element : Elemnts) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (element.getOption().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(element);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Element>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(Elemnts);
            }
            notifyDataSetChanged();
        }
    };

    public AutoCompleteElementAdapter(Context context, int textViewResourceId, List<Element> Elemnts) {
        super(context, textViewResourceId, Elemnts);
        // copy all the customers into a master list
        this.Elemnts = new ArrayList<Element>(Elemnts.size());
        this.Elemnts.addAll(Elemnts);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.auto_complete_element, null);
        }

        Element element = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.Search_Element);
        name.setText(element.getOption());
        TextView type = (TextView) view.findViewById(R.id.SearchType);
        type.setText(element.getOption_type());
        ImageView Location = (ImageView) view.findViewById(R.id.LocationImage);
        Location.setImageResource(R.mipmap.location);

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}
