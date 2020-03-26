package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Counter {
    public static class GenreEntity {
        private final String name;
        private int count;

        private GenreEntity(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }
    }

    public static class IntervalEntity {
        private final int start, stop;
        private final String name;
        private int count;
        private double ratio;

        public String getName() {
            return name;
        }

        public IntervalEntity(String name, int start, int stop) {
            this.name = name;
            this.start = start;
            this.stop = stop;
        }

        public int getStart() {
            return start;
        }

        public int getStop() {
            return stop;
        }

        public double getRatio(){
            return ratio;
        }

        public int getCount(){
            return count;
        }
    }

    private List<GenreEntity> genreList;
    private HashMap<String, List<IntervalEntity>> sizeMap;
    private File root;
    private List<Integer> sizeInterval;
    private List<String> intervalNames;

    public Counter(File root, List<Integer> sizeInterval, List<String> intervalNames) throws FileNotFoundException {
        this.root = root;
        this.genreList = new ArrayList<>();
        this.sizeInterval = sizeInterval;
        this.intervalNames = intervalNames;
        this.sizeMap = new HashMap<>();
        init();
    }

    private void init() throws FileNotFoundException {
        if (root.listFiles() == null) throw new FileNotFoundException("The root is incorrect");
        for (File firstLevelDir : root.listFiles()) {
            if(firstLevelDir.isFile()) continue;
            GenreEntity genreEntity = new GenreEntity(firstLevelDir.getName());
            List<IntervalEntity> intervalList = new ArrayList<>();
            intervalList.add(new IntervalEntity(intervalNames.get(0),0, sizeInterval.get(0)));
            for (int i = 1; i < sizeInterval.size(); i++) {
                intervalList.add(new IntervalEntity(intervalNames.get(i),sizeInterval.get(i - 1), sizeInterval.get(i)));
            }
            intervalList.add(new IntervalEntity(intervalNames.get(intervalNames.size()-1),sizeInterval.get(sizeInterval.size() - 1), Integer.MAX_VALUE));
            genreEntity.count += recursionCount(intervalList, firstLevelDir);
            genreList.add(genreEntity);
            double sum = 0;
            for (IntervalEntity intervalEntity : intervalList) {
                sum += intervalEntity.count;
            }
            for (IntervalEntity intervalEntity : intervalList) {
                intervalEntity.ratio = intervalEntity.count / sum;
            }
            sizeMap.put(firstLevelDir.getName(), intervalList);
        }

    }

    private int recursionCount(List<IntervalEntity> intervalEntities, File now) {
        if (now.isFile()) {
            for (int i = 0; i < sizeInterval.size(); i++) {
                if (now.length() < sizeInterval.get(i)) {
                    intervalEntities.get(i).count++;
                    return 1;
                }
            }
            intervalEntities.get(intervalEntities.size() - 1).count++;
            return 1;
        }
        int re = 0;
        for (File file : now.listFiles()) {
            re += recursionCount(intervalEntities, file);
        }
        return re;
    }

    public List<GenreEntity> getGenreEntities() {
        return Collections.unmodifiableList(genreList);
    }

    public List<IntervalEntity> getIntervalEntitiesByGenre(String genre) {
        return Collections.unmodifiableList(sizeMap.get(genre));
    }
}
