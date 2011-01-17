[Y,FS] = wavread ('clarinetharmonic');
figure;stem (Y); % dit moet eenmalig voor het overzicht

start = [    1  59100 130000 200000 270000 335000];
eind  = [45000 120000 190000 255000 330000 390000];

for(i=1:6)
    y = Y(start(i):eind(i));  %eerste stuk
    figure;stem(y,'.');
    axis tight;
    title(['klarinet #',int2str(i)]);
    N=length(y);
    Pxx = abs (fft (y,N))/N;
    dF = FS/N;
    F = dF*(0:(N-1)/2)';
    genoeg = 10000;
    figure;stem (F(1:genoeg),Pxx (1:genoeg),'.');
    title(['FFT van stuk klarinet #',int2str(i)]);
end;
