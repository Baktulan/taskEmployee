package org.example.dao.impl;

import org.example.config.Config;
import org.example.dao.JobDao;
import org.example.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    @Override
    public void createJobTable() {
        String sql= " create table if not exists jobs("+
                "id serial primary key,"+
                "position varchar,"+
                "profession varchar,"+
                "description varchar,"+
                "experience int)";
        try{
            Connection connection = Config.getConnection();
            Statement statement = connection.createStatement();
            System.out.println("Successfully created");
            statement.executeUpdate(sql);
            statement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void addJob(Job job) {
        String sql= " insert into jobs("+
                "position,profession,description,experience)"+
                "values (?,?,?,?)";
        try{
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            System.out.println(" successfully added");
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public Job getJobById(Long jobId) {
        Job job = null;
        String sql = "select * from jobs where jobs.id=?";
        try{
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement= connection.prepareStatement(sql);
               preparedStatement.setLong(1,jobId);
            ResultSet resultSet= preparedStatement.executeQuery();
              if (resultSet.next()){
                  job = new Job();
                    job.setId(resultSet.getLong("id"));
                    job.setPosition(resultSet.getString("position"));
                    job.setProfession(resultSet.getString("profession"));
                    job.setDescription(resultSet.getString("description"));
                    job.setExperience(resultSet.getInt("experience"));
              }
              resultSet.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return job ;
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        String sql=" select * from jobs order by experience "+ ascOrDesc;
        try{
            Connection connection = Config.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                jobs.add(new Job(
                resultSet.getLong("id"),
                resultSet.getString("Position"),
                resultSet.getString("Profession"),
                resultSet.getString("description"),
                resultSet.getInt("experience")));
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        Job job = null;
        String sql= """
             select * from jobs inner join employees on jobs.id=employees.job_id where employees.id=?
                """;
        try {
            Connection connection = Config.getConnection() ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
               job= new Job();
               job.setId(resultSet.getLong("id"));
               job.setPosition(resultSet.getString("position"));
               job.setProfession(resultSet.getString("profession"));
               job.setDescription(resultSet.getString("description"));
               job.setExperience(resultSet.getInt("experience"));
            } else {
                throw new Exception("not found");
            }
            preparedStatement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return job;
    }

    @Override
    public void deleteDescriptionColumn() {
        String sql= """
                alter table jobs drop column description
                """;
        try {
            Connection connection = Config.getConnection();
            Statement statement = connection.createStatement();
            System.out.println("Description successfully deleted");
            statement.executeUpdate(sql);
            statement.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
