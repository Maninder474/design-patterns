package com.opentext.patterns.command.remote;

import com.opentext.patterns.command.items.Stereo;

public class StereoCommand implements Command {
    private Stereo stereo;
    public StereoCommand(Stereo stereo) {
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume(11);
    }
}
