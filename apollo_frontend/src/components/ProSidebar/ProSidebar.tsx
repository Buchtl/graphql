import { Sidebar, Menu, MenuItem, SubMenu } from 'react-pro-sidebar';

function ProSidebar() {
  return (
    <div id="app" style={{ height: "33", display: "100vh" }}>
      <Sidebar>
        <Menu>
          <SubMenu label="Charts">
            <MenuItem> Pie charts </MenuItem>
            <MenuItem> Line charts </MenuItem>
          </SubMenu>
          <MenuItem> Documentation </MenuItem>
          <MenuItem> Calendar </MenuItem>
        </Menu>
      </Sidebar>;
    </div>
  );

}

export default ProSidebar