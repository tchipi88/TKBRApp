(function() {
    'use strict';

    angular
        .module('app')
        .controller('UserManagementDialogController',UserManagementDialogController);

    UserManagementDialogController.$inject = ['$stateParams', '$uibModalInstance', 'entity', 'User','Authority'];

    function UserManagementDialogController ($stateParams, $uibModalInstance, entity, User,Authority) {
        var vm = this;
        var tab;

       // vm.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        
        vm.authorities=Authority.query();
        vm.clear = clear;
        vm.languages = null;
        vm.save = save;
        vm.user = entity;

        
        function Transform(authorities)
        {
        	var authoritiesString=[]; var i=0 ;var count=0;
        	
        	  for(var authority in authorities){
        	   count++;
        	   alert(authority.name);
        	  }
        	
        	
        	alert(authorities.length);
        	alert(count);
            for(i=0;i<authorities.length;i++)
            	authoritiesString[i]=authorities[i].name;
            	//authoritiesString.push({authorities[i].id});
            return authoritiesString;
        }
       

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            $uibModalInstance.close(result);
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                User.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                User.save(vm.user, onSaveSuccess, onSaveError);
            }
        }
    }
})();
