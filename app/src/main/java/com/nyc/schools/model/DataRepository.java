package com.nyc.schools.model;

import com.nyc.schools.utils.CommonUtil;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vijaya.eluri on 4/24/2019.
 */

public class DataRepository {

    private static DataRepository instance = null;
    private static Bus eventBus;
    private List<School> schools;
    private List<SchoolScore> schoolScores;

    public static DataRepository getInstance() {
        if (null == instance) {
            instance = new DataRepository();
            eventBus = new Bus(ThreadEnforcer.MAIN);
        }
        return instance;
    }

    public List<School> getSchools(String type) {
        if (DataRepository.getInstance().getSchools() == null) {
            return null;
        }
        if (type.equalsIgnoreCase("fav")) {
            return DataRepository.getInstance().getSchools(false);

        } else if (type.equalsIgnoreCase("sort_name")) {
            return DataRepository.getInstance().getSortedSchoolsByName();

        } else if (type.equalsIgnoreCase("sort_grade")) {
            return DataRepository.getInstance().getSortedSchoolsByGradRate();
        }

        return DataRepository.getInstance().getSchools(true);
    }

    private List<School> getSchools(boolean bAll) {
        if (schools != null && !bAll) {
            List<School> list = new ArrayList<>();
            for (School school : schools) {
                if (school.isFavorite()) {
                    list.add(school);
                }
            }
            return list;
        }
        return schools;
    }

    public List<School> getSchools() {
        return schools;
    }

    public void setSchools(List<School> schools) {
        this.schools = schools;
    }

    public List<SchoolScore> getSchoolScores() {
        return schoolScores;
    }

    public void setSchoolScores(List<SchoolScore> schoolScores) {
        this.schoolScores = schoolScores;
    }

    public Bus getEventBus() {
        return eventBus;
    }

    public void setEventBus(Bus eventBus) {
        DataRepository.eventBus = eventBus;
    }

    public static class RepoUpdated {
        public boolean updated = false;
    }

    public void postRepoUpdated() {
        DataRepository.RepoUpdated object = new DataRepository.RepoUpdated();
        object.updated = true;
        DataRepository.getInstance().getEventBus().post(object);
    }

    public List<School> getSortedSchoolsByName() {
        List<School> list = getSchools();
        Collections.sort(list, new Comparator<School>() {
            @Override
            public int compare(School lhs, School rhs) {
                return lhs.getSchoolName().compareToIgnoreCase(rhs.getSchoolName());
            }
        });
        return list;
    }

    public List<School> getSortedSchoolsByGradRate() {
        List<School> list = getSchools();
        Collections.sort(list, new Comparator<School>() {
            @Override
            public int compare(School lhs, School rhs) {
                return Double.valueOf(CommonUtil.isEmptyString(rhs.getGraduationRate()) ? "0" : rhs.getGraduationRate())
                        .compareTo(Double.valueOf(CommonUtil.isEmptyString(lhs.getGraduationRate()) ? "0" : lhs.getGraduationRate()));
            }
        });
        return list;
    }
}
