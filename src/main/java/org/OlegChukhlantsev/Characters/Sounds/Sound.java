package org.OlegChukhlantsev.Characters.Sounds;

import org.OlegChukhlantsev.CommonManagers.PropertiesManager;
import org.OlegChukhlantsev.CommonManagers.ThreadsWaiting;
import org.OlegChukhlantsev.Enums.CharacterTypes;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

import org.OlegChukhlantsev.Characters.Character;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements AutoCloseable {
    private boolean released = false;
    private AudioInputStream stream = null;
    private Clip clip = null;
    private FloatControl volumeControl = null;
    private boolean playing = false;

    public Sound(String pathToFile) {
        try {
            File f = new File(Objects.requireNonNull(Sound.class.getResource(pathToFile)).toURI());

            stream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(stream);
            clip.addLineListener(new Listener());
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            released = true;
        } catch (IOException |URISyntaxException| UnsupportedAudioFileException | LineUnavailableException exc) {
            exc.printStackTrace();
            released = false;

            close();

        }
    }

    public Sound(String pathToFile, float volume) {

       this(pathToFile);
       setVolume(volume);


    }


    // true если звук успешно загружен, false если произошла ошибка
    public boolean isReleased() {
        return released;
    }

    // проигрывается ли звук в данный момент
    public boolean isPlaying() {
        return playing;
    }

    // Запуск
	/*
	  breakOld определяет поведение, если звук уже играется
	  Если breakOld==true, о звук будет прерван и запущен заново
	  Иначе ничего не произойдёт
	*/
    public void play(boolean breakOld) {
        if (released) {
            if (breakOld) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            } else if (!isPlaying()) {
                clip.setFramePosition(0);
                clip.start();
                playing = true;
            }
        }
    }


    // То же самое, что и play(true)
    public void play() {
        play(true);
    }

    // Останавливает воспроизведение
    public void stop() {
        if (playing) {
            clip.stop();
        }
    }

    public void close() {
        if (clip != null)
            clip.close();

        if (stream != null)
            try {
                stream.close();
            } catch (IOException exc) {
                exc.printStackTrace();
            }
    }

    // Установка громкости
	/*
	  x долже быть в пределах от 0 до 1 (от самого тихого к самому громкому)
	*/
    public void setVolume(float x) {
        if (x<0) x = 0;
        if (x>1) x = 1;
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        volumeControl.setValue((max-min)*x+min);
    }

    // Возвращает текущую громкость (число от 0 до 1)
    public float getVolume() {
        float v = volumeControl.getValue();
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        return (v-min)/(max-min);
    }

    // Дожидается окончания проигрывания звука
    public void join() {
        if (!released) return;
        synchronized(clip) {
            try {
                while (playing)
                    clip.wait();
            } catch (InterruptedException ignored) {}
        }
    }

    // Статический метод, для удобства
    public static Sound playSound(String path) {

            Sound snd = new Sound(path);
            snd.play();

                return snd;


    }

    public static void replayHitSound(CharacterTypes characterType) {

        String pathToFile = "/music/"+characterType+"/hit.wav";
        playSound(pathToFile);
    }

    public static void replayMissSound(CharacterTypes characterType) {

        String pathToFile = "/music/"+characterType+"/miss.wav";
        playSound(pathToFile);
    }

    public static void replayDieSound(CharacterTypes characterType) {

        String pathToFile = "/music/"+characterType+"/die.wav";
        playSound(pathToFile);
    }



    private class Listener implements LineListener {
        public void update(LineEvent ev) {
            if (ev.getType() == LineEvent.Type.STOP) {
                playing = false;
                synchronized(clip) {
                    clip.notify();
                }
            }
        }

    }




/*
public static void replayHitSound(CharacterTypes characterType) {


        String pathToFile = "/music/"+characterType+"/hit.wav";
        replayMusic(pathToFile);
}

    public static void replayMissSound(CharacterTypes characterType) {


        String pathToFile = "/music/"+characterType+"/miss.wav";
        replayMusic(pathToFile);
    }

    public static void replayMusic(String pathToFile) {
        Thread thread = new Thread(() -> {
            try {

                File soundFile = new File(Objects.requireNonNull(SoundManager.class.getResource(pathToFile)).toURI());
                //"/music/1.wav"); //Звуковой файл

                AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);

                //Получаем реализацию интерфейса Clip
                //Может выкинуть LineUnavailableException
                Clip clip = AudioSystem.getClip();

                //Загружаем наш звуковой поток в Clip
                //Может выкинуть IOException и LineUnavailableException
                clip.open(ais);

                clip.setFramePosition(0); //устанавливаем указатель на старт
                clip.start(); //Поехали!!!

            } catch (IOException | UnsupportedAudioFileException | LineUnavailableException exc) {
                exc.printStackTrace();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

        });
        thread.start();
    }*/


}
