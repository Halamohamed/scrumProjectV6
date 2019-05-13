package se.BTH.ITProjectManagement.handling;

import static org.apache.log4j.BasicConfigurator.configure;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.BTH.ITProjectManagement.models.*;
import se.BTH.ITProjectManagement.repositories.*;
import se.BTH.ITProjectManagement.services.SprintService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class Initializer implements CommandLineRunner {

    private final UserRepository userrepo;
    private final RoleRepository rolerepo;
    private final TeamRepository teamrepo;
    private final TaskRepository taskRepo;
    private final SubTaskRepository subTaskRepo;
    private final SprintRepository sprintRepo;
    private final SprintService sprintService;


    public Initializer(UserRepository repository, RoleRepository rolerepo, TeamRepository teamrepo, TaskRepository taskRepo, SubTaskRepository subTaskRepo, SprintRepository sprintRepo, SprintService sprintService) {
        this.userrepo = repository;
        this.rolerepo = rolerepo;
        this.teamrepo = teamrepo;
        this.taskRepo = taskRepo;
        this.subTaskRepo = subTaskRepo;
        this.sprintRepo = sprintRepo;
        this.sprintService = sprintService;
    }


    @Override
    public void run(String... strings) {
//        teamrepo.deleteAll();
//        rolerepo.deleteAll();
//        userrepo.deleteAll();
//        taskRepo.deleteAll();
//        subTaskRepo.deleteAll();
//        sprintRepo.deleteAll();
//        addPersons();
//        addBacklog();
//        statistics();

    }

    private void statistics() {
        Sprint sprint = sprintRepo.findById("5cd9340fa6b5631facc55183").get();
        List<Double> aD = sprint.Actual_hours_today_sum();
        List<Double> aR = sprint.Calculate_actual_remaining();
        List<Double> pR = sprint.Calculate_planned_remaining();
        List<Double> pD = new ArrayList<>();
        for (int i = 0; i < sprint.getPlannedPeriod(); i++) {
            pD.add(sprint.Calculate_Planned_hours_today());
        }
        System.out.println("estimated = " + sprint.Calculate_total_estimate());
        aD.forEach(System.out::println);
        System.out.println("--------------------------");
        pD.forEach(System.out::println);
        System.out.println("--------------------------");
        aR.forEach(System.out::println);
        System.out.println("--------------------------");

        pR.forEach(System.out::println);
    }
    private void addBacklog() {
        List<Sprint> sprints= new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        //add first task
        List<User> users = new ArrayList<>();
        List<Integer> actualHours = new ArrayList<>();
        List<SubTask> subTasks = new ArrayList<>();
        actualHours.add( 1);
        actualHours.add( 0);
        actualHours.add( 4);
        actualHours.add( 1);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        User user = userrepo.findByUsername("Arti");
        users.add(user);
        SubTask subtask = SubTask.builder().name("Schema Registration").status(TaskStatus.DONE).users(users)
                .OEstimate(8).actualHours(actualHours).build();
        subtask.getActualHours().forEach(System.out::println);
        subTasks.add(subtask);

        users = new ArrayList<>();
        actualHours= new ArrayList<>();
        actualHours.add(0, 8);
        actualHours.add(1, 4);
        actualHours.add(2, 6);
        actualHours.add(3, 6);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Hossein");
        users.add(user);
        user = userrepo.findByUsername("Kevin");
        users.add(user);
        subtask = SubTask.builder().name("implement notification contxt and data").status(TaskStatus.ONGOING).users(users)
                .OEstimate(40).actualHours(actualHours).build();
        subTasks.add(subtask);

        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        actualHours.add(0, 8);
        actualHours.add(1, 4);
        actualHours.add(2, 6);
        actualHours.add(1, 6);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Hossein");
        users.add(user);
        subtask = SubTask.builder().name("New notification category").status(TaskStatus.REVIEW).users(users)
                .OEstimate(2).actualHours(actualHours).build();
        subTasks.add(subtask);
        subTaskRepo.saveAll(subTasks);
        Task task = Task.builder().name("Implement of new OTC notification").priority(1).storyPoints(50).subTasks(subTasks)
                .build();
        tasks.add(task);
        // add  task 2
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        subTasks = new ArrayList<>();
        actualHours.add(0, 1);
        actualHours.add(1, 1);
        actualHours.add(2, 0);
        actualHours.add(3, 2);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Emil");
        users.add(user);
        subtask = SubTask.builder().name("Test Analysis").status(TaskStatus.ONGOING).users(users)
                .OEstimate(24).actualHours(actualHours).build();
        subTasks.add(subtask);

        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        actualHours.add(0, 7);
        actualHours.add(1, 7);
        actualHours.add(2, 8);
        actualHours.add(3, 1);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Emil");
        users.add(user);

        subtask = SubTask.builder().name("test data( RMCA & CPM)").status(TaskStatus.ONGOING).users(users)
                .OEstimate(40).actualHours(actualHours).build();
        subTasks.add(subtask);
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        actualHours.add(0, 0);
        actualHours.add(1, 0);
        actualHours.add(2, 0);
        actualHours.add(3, 2);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Emil");
        users.add(user);

        subtask = SubTask.builder().name("integration Test").status(TaskStatus.ONGOING).users(users)
                .OEstimate(40).actualHours(actualHours).build();
        subTasks.add(subtask);
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        actualHours.add(0, 4);
        actualHours.add(1, 0);
        actualHours.add(2, 0);
        actualHours.add(3, 0);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Forhan");
        users.add(user);
        subtask = SubTask.builder().name("Jive Test").status(TaskStatus.PLANNED).users(users)
                .OEstimate(64).actualHours(actualHours).build();
        subTasks.add(subtask);
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        actualHours.add(0, 0);
        actualHours.add(1, 0);
        actualHours.add(2, 0);
        actualHours.add(3, 1);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Forhan");
        users.add(user);
        subtask = SubTask.builder().name("Jive Protocol").status(TaskStatus.ONGOING).users(users)
                .OEstimate(8).actualHours(actualHours).build();
        subTasks.add(subtask);
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        actualHours.add(0, 2);
        actualHours.add(1, 0);
        actualHours.add(2, 12);
        actualHours.add(3, 11);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Simon");
        users.add(user);
        user = userrepo.findByUsername("Forhan");
        users.add(user);
        subtask = SubTask.builder().name("XML schemas").status(TaskStatus.ONGOING).users(users)
                .OEstimate(10).actualHours(actualHours).build();
        subTasks.add(subtask);
        subTaskRepo.saveAll(subTasks);
        task = Task.builder().name("Verification").priority(2).storyPoints(178).subTasks(subTasks)
                .build();
        tasks.add(task);
//add task 3
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        subTasks = new ArrayList<>();
        actualHours.add(0, 0);
        actualHours.add(1, 0);
        actualHours.add(2, 0);
        actualHours.add(3, 0);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Arti");
        users.add(user);
        subtask = SubTask.builder().name("BSUCstudy doc update").status(TaskStatus.ONGOING).users(users)
                .OEstimate(16).actualHours(actualHours).build();
        subTasks.add(subtask);

        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        actualHours.add(0, 0);
        actualHours.add(1, 0);
        actualHours.add(2, 0);
        actualHours.add(3, 0);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Forhan");
        users.add(user);

        subtask = SubTask.builder().name("Maven site update").status(TaskStatus.ONGOING).users(users)
                .OEstimate(16).actualHours(actualHours).build();
        subTasks.add(subtask);
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        actualHours.add(0, 0);
        actualHours.add(1, 0);
        actualHours.add(2, 0);
        actualHours.add(3, 2);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Emil");
        users.add(user);

        subtask = SubTask.builder().name("PI documentation update").status(TaskStatus.ONGOING).users(users)
                .OEstimate(16).actualHours(actualHours).build();

        subTasks.add(subtask);
        subTaskRepo.saveAll(subTasks);

        task = Task.builder().name("Documentation").priority(2).storyPoints(48).subTasks(subTasks)
                .build();
        tasks.add(task);
        //add task 4
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        subTasks = new ArrayList<>();
        actualHours.add(0, 0);
        actualHours.add(1, 0);
        actualHours.add(2, 0);
        actualHours.add(3, 1);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Emil");
        users.add(user);
        subtask = SubTask.builder().name("New feature Control").status(TaskStatus.ONGOING).users(users)
                .OEstimate(80).actualHours(actualHours).build();
        subTasks.add(subtask);
        subTaskRepo.saveAll(subTasks);

        task = Task.builder().name("Introduction new feature Control").priority(4).storyPoints(8).subTasks(subTasks)
                .build();
        tasks.add(task);
//add task 5
        users = new ArrayList<>();
        actualHours = new ArrayList<>();
        subTasks = new ArrayList<>();
        actualHours.add(0, 6);
        actualHours.add(1, 0);
        actualHours.add(2, 6);
        actualHours.add(3, 6);
        for (int i= 4; i<14; i++){
            actualHours.add(0);
        }
        user = userrepo.findByUsername("Arti");
        users.add(user);
        user = userrepo.findByUsername("Hossein");
        users.add(user);
        user = userrepo.findByUsername("Kevin");
        users.add(user);
        user = userrepo.findByUsername("Emil");
        users.add(user);
        user = userrepo.findByUsername("Forhan");
        users.add(user);
        user = userrepo.findByUsername("Simon");
        users.add(user);
        subtask = SubTask.builder().name("Analyis").status(TaskStatus.ONGOING).users(users)
                .OEstimate(80).actualHours(actualHours).build();
        subTasks.add(subtask);
        subTaskRepo.saveAll(subTasks);
        task = Task.builder().name("Analysis").priority(5).storyPoints(80).subTasks(subTasks)
                .build();
        tasks.add(task);
        taskRepo.saveAll(tasks);

        Sprint sprint = Sprint.builder().plannedPeriod(14).tasks(tasks).team(teamrepo.findByName("Team1").get()).name("CFT55 sprint 1910-1912").daily_meeting(LocalTime.of(13, 00))
                .delivery(LocalDate.of(2019, 3, 22)).demo(LocalDate.of(2019, 3, 21)).startSprint(LocalDate.of(2019, 3, 5)).demo(LocalDate.of(2019, 3, 21))
                .goal("Finish TR HX33029").retrospective(LocalDate.of(2019, 3, 21)).review(DayOfWeek.FRIDAY).build();
        sprint.calcDelivery();
        sprintRepo.save(sprint);
        sprints.add(sprint);
        Sprint sprint1=sprintRepo.findByName("CFT55 sprint 1910-1912").get();
       //System.out.println(sprint);
        System.out.println(sprint1.getDelivery().toString());
        System.out.println(sprint1.getStartSprint().toString());
    }


    private void addPersons() {
        Role role = Role.builder().name(RoleName.ROLE_USER).build();
        rolerepo.save(role);
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        role = Role.builder().name(RoleName.ROLE_ADMIN).build();
        rolerepo.save(role);
        List<User> users = new ArrayList<>();
        User u = User.builder().name("Arti").email("arti@bth.se").active(true).phone("0760762135").city("Karlskrona")
                .username("Arti").password("1111").passwordConfirm("1111").roles(roles).build();
        users.add(u);
        u = User.builder().name("Hossein").email("hossein@bth.se").active(true).phone("0770772135").city("Karlshamen")
                .username("Hossein").password("2222").passwordConfirm("2222").roles(roles).build();
        users.add(u);
        u = User.builder().name("Kevin").email("fridrek@bth.se").active(true).phone("0770772135").city("Karlshamen")
                .username("Kevin").password("3333").passwordConfirm("3333").roles(roles).build();
        users.add(u);
        u = User.builder().name("Emil").email("emil@bth.se").active(true).phone("0770772135").city("Karlshamen")
                .username("Emil").password("4444").passwordConfirm("4444").roles(roles).build();
        users.add(u);
        u = User.builder().name("Forhan").email("forhan@bth.se").active(true).phone("0770772135").city("Karlshamen")
                .username("Forhan").password("5555").passwordConfirm("5555").roles(roles).build();
        users.add(u);
        u = User.builder().name("Simon").email("simon@bth.se").active(true).phone("0770772135").city("Karlshamen")
                .username("Simon").password("6666").passwordConfirm("6666").roles(roles).build();
        users.add(u);
        userrepo.saveAll(users);

        Team team = Team.builder().name("Team1").active(true).users(users).build();
        teamrepo.save(team);
        users = new ArrayList<>();
        u = User.builder().name("Shatha").email("shatha@bth.se").active(true).phone("0760762135").city("Karlskrona")
                .username("Shatha").password("7777").passwordConfirm("7777").roles(roles).build();
        users.add(u);
        u = User.builder().name("Hala").email("hala@bth.se").active(true).phone("0770772135").city("Karlshamen")
                .username("Hala").password("8888").passwordConfirm("8888").roles(roles).build();
        users.add(u);
        u = User.builder().name("Robal").email("robal@bth.se").active(true).phone("0770772135").city("Karlshamen")
                .username("Robal").password("9999").passwordConfirm("9999").roles(roles).build();
        users.add(u);
        userrepo.saveAll(users);
        team = Team.builder().name("Team2").active(true).users(users).build();
        teamrepo.save(team);

        roles.add(role);
        users = new ArrayList<>();
        u = User.builder().name("Administrator").email("Admin@bth.se").active(true).phone("0760744135").city("Karlskrona")
                .username("Administrator").password("Administrator").passwordConfirm("Administrator").roles(roles).build();
        users.add(u);
        userrepo.saveAll(users);
        team = Team.builder().name("Team Administrators").active(true).users(users).build();
        teamrepo.save(team);
      //teamrepo.findByName("Team Administrators").get().getUsers().forEach(System.out::println);
        //  teamrepo.findAll().forEach(System.out::println);
    }

}