package be.thomasdewulf.whoisit.fragments;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.activities.MainActivity;
import be.thomasdewulf.whoisit.databinding.FragmentAddCharacterBinding;
import be.thomasdewulf.whoisit.ui.AddCharacterImageCallback;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCharacterFragment extends DialogFragment
{
    private FragmentAddCharacterBinding binding;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String photoPath;
    private AwesomeValidation validation;

    public static final String INTENT_EXTRA_NAME = "intent_extra_name";
    public static final String INTENT_EXTRA_DESCRIPTION = "intent_extra_description";
    public static final String INTENT_EXTRA_PHOTO = "intent_extra_photo";


private final AddCharacterImageCallback characterImageCallback = () ->
{
    File photoFile = null;
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    try {
        photoFile = createImageFile();
    } catch (IOException ex) {
        // Error occurred while creating the File
        Toast.makeText(getContext(),"Bestand voor foto kon niet aangemaakt worden. Probeer opnieuw",Toast.LENGTH_LONG).show();
    }
    // Continue only if the File was successfully created
    if (photoFile != null) {
        Uri photoURI = FileProvider.getUriForFile(getContext(),
                "be.thomasdewulf.whoisit.fileprovider",
                photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
};

    public AddCharacterFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_add_character, container, false);
        binding.setCallback(characterImageCallback);


        MainActivity activity =(MainActivity) getActivity();
        activity.setSupportActionBar(binding.toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        validation = new AwesomeValidation(ValidationStyle.UNDERLABEL);
        validation.setContext(getContext());
        validation.addValidation(binding.characterName, RegexTemplate.NOT_EMPTY, "Naam is verplicht.");
        validation.addValidation(binding.characterDescription, RegexTemplate.NOT_EMPTY, "Beschrijving is verplicht.");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_character,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_save)
        {
            //TODO: validation toevoegen
if(validation.validate())
{


    Intent intent = new Intent();
    intent.putExtra(INTENT_EXTRA_NAME, binding.characterName.getText().toString());
    intent.putExtra(INTENT_EXTRA_DESCRIPTION, binding.characterDescription.getText().toString());
    intent.putExtra(INTENT_EXTRA_PHOTO, photoPath);
    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    dismiss();
    return true;
}

        }
        else if(id == android.R.id.home)
        {
            getTargetFragment().onActivityResult(101, Activity.RESULT_OK, null);
            dismiss();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
           //Show image in imageview
            Picasso.with(getContext()).load("file:///" + photoPath).fit().into(binding.imageView);

        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoPath = image.getAbsolutePath();
        return image;
    }
}
