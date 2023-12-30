import torch
import torch.nn as nn
import torch.nn.functional as F
import numpy as np
import gym
from torch.utils.tensorboard import SummaryWriter

# gym=0.26.0 https://blog.csdn.net/qq_43674552/article/details/127344366

# Hyper Parameters
EPOCH = 400    
BATCH_SIZE = 32
LR = 0.01                   # learning rate
EPSILON = 0.9               # greedy policy
GAMMA = 0.9                 # reward discount
TARGET_REPLACE_ITER = 100   # target update frequency
MEMORY_CAPACITY = 2000
env = gym.make('CartPole-v1', render_mode = "human")
env = env.unwrapped
N_ACTIONS = env.action_space.n
N_STATES = env.observation_space.shape[0]

class Net(nn.Module):
    
    def __init__(self):
        super(Net, self).__init__()
        self.fc1 = nn.Linear(N_STATES, 20)
        self.fc1.weight.data.normal_(0, 0.1) 
        self.fc2 = nn.Linear(20, N_ACTIONS)
        self.fc2.weight.data.normal_(0, 0.1) 

    def forward(self, x):
        x = F.relu(self.fc1(x))
        return self.fc2(x)

class DQN(object):

    def __init__(self):
        self.target_net, self.evaluate_net = Net(), Net()
        self.memory = np.zeros((MEMORY_CAPACITY, N_STATES * 2 + 2))
        self.loss_Function = nn.MSELoss()
        self.optimizer = torch.optim.Adam(self.evaluate_net.parameters(), lr=LR)
        self.point = 0
        self.learn_step = 0

    def choose_action(self, s):
        s = torch.unsqueeze(torch.FloatTensor(s), 0)
        if np.random.uniform() < EPSILON:	# epsilon-greedy
            return torch.max(self.evaluate_net.forward(s), 1)[1].data.numpy()[0]
        else:
            return np.random.randint(0, N_ACTIONS)

    def store_transition(self, s, a, r, s_):
        self.memory[self.point % MEMORY_CAPACITY, :] = np.hstack((s, [a, r], s_))
        self.point += 1

    def sample_batch_data(self, batch_size):
        perm_idx = np.random.choice(len(self.memory), batch_size)
        return self.memory[perm_idx]

    def learn(self) -> float:
        if self.learn_step % TARGET_REPLACE_ITER == 0:
            self.target_net.load_state_dict(self.evaluate_net.state_dict())
        self.learn_step += 1
        
        batch_memory = self.sample_batch_data(BATCH_SIZE)
        batch_state = torch.FloatTensor(batch_memory[:, :N_STATES])
        batch_action = torch.LongTensor(batch_memory[:, N_STATES : N_STATES + 1].astype(int))
        batch_reward = torch.FloatTensor(batch_memory[:, N_STATES + 1 : N_STATES + 2])
        batch_next_state = torch.FloatTensor(batch_memory[:, -N_STATES:])

        q_eval = self.evaluate_net(batch_state).gather(1, batch_action)
        q_next = self.target_net(batch_next_state).detach()
        q_target = batch_reward + GAMMA * q_next.max(1)[0].view(BATCH_SIZE, 1)
        loss = self.loss_Function(q_eval, q_target)

        self.optimizer.zero_grad()
        loss.backward()
        self.optimizer.step()

        return loss.data.numpy()

dqn = DQN()

writer = SummaryWriter("run/MemoryCapacity_100_CustomReward/")
writer.add_graph(dqn.evaluate_net, torch.randn(1, N_STATES))

global_step = 0
for i in range(EPOCH):
    s = env.reset(seed=1)[0]
    running_loss = 0
    cumulated_reward = 0
    step = 0

    while True:
        global_step += 1
        env.render()
        a = dqn.choose_action(s)
        s_, r, done, _, _ = env.step(a)

		# �Զ���reward������ԭʼ��reward
        x, x_dot, theta, theta_dot = s_
        r1 = (env.x_threshold - abs(x)) / env.x_threshold - 0.8
        r2 = (env.theta_threshold_radians - abs(theta)) / env.theta_threshold_radians - 0.5
        r = r1 + r2
        
        dqn.store_transition(s, a, r, s_)

        cumulated_reward += r
        if dqn.point > MEMORY_CAPACITY:	# �ھ��������֮��ſ�ʼ����ѧϰ
            loss = dqn.learn()
            running_loss += loss
            if done or step > 2000:
                print("��FAIL��Episode: %d| Step: %d| Loss:  %.4f, Reward: %.2f" % (i, step, running_loss / step, cumulated_reward))
                writer.add_scalar("training/Loss", running_loss / step, global_step)
                writer.add_scalar("training/Reward", cumulated_reward, global_step)
                break
        else:
            print("\rCollecting experience: %d / %d..." %(dqn.point, MEMORY_CAPACITY), end='')

        if done:
            break
        if step % 100 == 99:
            print("Episode: %d| Step: %d| Loss:  %.4f, Reward: %.2f" % (i, step, running_loss / step, cumulated_reward))
        step += 1
        s = s_
