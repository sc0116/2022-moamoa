import type { Story } from '@storybook/react';
import { useState } from 'react';

import SideMenu from '@study-room-page/components/side-menu/SideMenu';
import type { SideMenuProps } from '@study-room-page/components/side-menu/SideMenu';
import type { TabId, Tabs } from '@study-room-page/hooks/useStudyRoomPage';

export default {
  title: 'Components/SideMenu',
  component: SideMenu,
};

const Template: Story<SideMenuProps> = () => {
  const tabs: Tabs = [
    { id: 'notice', name: '공지사항', content: '공지사항입니다.' },
    { id: 'material', name: '자료실', content: '자료실입니다.' },
    { id: 'review', name: '후기', content: '후기입니다.' },
  ];

  const [activeTabId, setActiveTabId] = useState<TabId>(tabs[0].id);

  const handleTabButtonClick = (id: TabId) => () => {
    setActiveTabId(id);
  };
  return <SideMenu activeTabId={activeTabId} tabs={tabs} onTabButtonClick={handleTabButtonClick} />;
};

export const Default = Template.bind({});
Default.parameters = { controls: { exclude: ['className', 'activeTabId', 'tabs', 'onTabButtonClick'] } };