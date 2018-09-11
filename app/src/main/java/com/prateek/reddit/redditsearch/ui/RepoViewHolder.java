package com.prateek.reddit.redditsearch.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.prateek.reddit.redditsearch.R;
import com.prateek.reddit.redditsearch.models.Repo;


public class RepoViewHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView description;
    private TextView stars;
    private TextView language;
    private TextView forks;
    private Resources resources;
    private View view;

    public RepoViewHolder(View itemView) {
        super(itemView);
        this.view=itemView;
        name=itemView.findViewById(R.id.repo_name);
        description=itemView.findViewById(R.id.repo_description);
        stars=itemView.findViewById(R.id.repo_stars);
        language=itemView.findViewById(R.id.repo_language);
        forks=itemView.findViewById(R.id.repo_forks);
        resources=itemView.getResources();
    }

    public void bind(Repo repo){
        if(repo==null){

            name.setText(resources.getString(R.string.loading));
            description.setVisibility(View.GONE);
            language.setVisibility(View.GONE);
            stars.setText(resources.getString(R.string.unknown));
            forks.setText(resources.getString(R.string.unknown));

        }else {
            showRepoData(repo);

        }
    }

    private void showRepoData(final Repo repo) {

        name.setText(repo.getFullName());

        // if the description is missing, hide the TextView

       int descriptionVisibility = View.GONE;
        if (repo.getDescription() != null) {
            description.setText(repo.getDescription());
            descriptionVisibility = View.VISIBLE;
        }
        description.setVisibility(descriptionVisibility);

        stars.setText(repo.getStars().toString());
        forks.setText(repo.getStars().toString());


        // if the language is missing, hide the label and the value
        int languageVisibility = View.GONE;
        if (repo.getLanguage()!=null &&  !repo.getLanguage().isEmpty()) {

            language.setText(repo.getLanguage());
            languageVisibility = View.VISIBLE;
        }
        language.setVisibility(languageVisibility);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!repo.getHtmlUrl().isEmpty() && repo!=null){
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(repo.getHtmlUrl()));
                    view.getContext().startActivity(intent);

                }
            }
        });
    }
}
