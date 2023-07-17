package xyz.frogdream.launcher.downloader;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.to2mbn.jmccc.auth.OfflineAuthenticator;
import org.to2mbn.jmccc.launch.LaunchException;
import org.to2mbn.jmccc.launch.Launcher;
import org.to2mbn.jmccc.launch.LauncherBuilder;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloader;
import org.to2mbn.jmccc.mcdownloader.MinecraftDownloaderBuilder;
import org.to2mbn.jmccc.mcdownloader.provider.DownloadProviderChain;
import org.to2mbn.jmccc.mcdownloader.provider.fabric.FabricDownloadProvider;
import org.to2mbn.jmccc.option.LaunchOption;
import org.to2mbn.jmccc.option.MinecraftDirectory;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;


public class Download {
    static MinecraftDirectory dir = new MinecraftDirectory(System.getenv("LOCALAPPDATA") + "/FrogDream");

    public static void download() {
        System.out.println("Downloading to " + dir.getRoot().toString());
        processDownload();
        downloadMods();
        System.out.println("Done");
    }


    static boolean processDownload() {
        FabricDownloadProvider fabricDownloadProvider = new FabricDownloadProvider();
        MinecraftDownloader downloader = MinecraftDownloaderBuilder.create()
                .providerChain(DownloadProviderChain.create()
                        .addProvider(fabricDownloadProvider))
                .build();

        try {
            downloader.downloadIncrementally(dir, "fabric-loader-0.14.21-1.20.1", null).get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }



        return true;
    }
    static String PREFIX = "https://new.frogdream.xyz/launcher/";
    public static void downloadMods() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PREFIX + "files"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to load file list");
            throw new RuntimeException(e);
        }
        String[] files = new Gson().fromJson(response.body(), String[].class);
        for (String file : files) {
            try {
                FileUtils.copyURLToFile(new URL(PREFIX + file), new File(dir.getRoot().toString() + file));
            } catch (IOException e) {
                System.err.println("Failed to download: " + file);
            }
        }
    }

    public static void launch(String nick) {
        if (!dir.getRoot().exists()) download();
        Launcher launcher = LauncherBuilder.buildDefault();
        try {
            launcher.launch(new LaunchOption("fabric-loader-0.14.21-1.20.1", OfflineAuthenticator.name(nick), dir));
        } catch (LaunchException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
