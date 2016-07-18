/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.job.mesos.facade;

import com.dangdang.ddframe.job.context.TaskContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.mesos.Protos;

import java.util.Collection;
import java.util.List;

/**
 * 分配完成的任务集合上下文.
 *
 * @author zhangliang
 */
@RequiredArgsConstructor
@Getter
@ToString
public final class AssignedTaskContext {
    
    private final List<Protos.TaskInfo> taskInfoList;
    
    private final Collection<TaskContext> failoverTaskContexts;
    
    private final Collection<String> misfiredJobNames;
    
    private final Collection<String> readyJobNames;
}
